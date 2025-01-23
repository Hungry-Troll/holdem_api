package net.lodgames.currency.diamond.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.currency.common.constants.ChangeType;
import net.lodgames.currency.diamond.constants.DiamondHistoryDesc;
import net.lodgames.currency.diamond.model.Diamond;
import net.lodgames.currency.diamond.model.DiamondRecord;
import net.lodgames.currency.diamond.param.DiamondCheatParam;
import net.lodgames.currency.diamond.param.DiamondDepositParam;
import net.lodgames.currency.diamond.param.DiamondWithdrawParam;
import net.lodgames.currency.diamond.repository.DiamondRecordRepository;
import net.lodgames.currency.diamond.repository.DiamondRepository;
import net.lodgames.currency.diamond.vo.DiamondDepositVo;
import net.lodgames.currency.diamond.vo.DiamondVo;
import net.lodgames.currency.diamond.vo.DiamondWithdrawVo;
import net.lodgames.user.repository.UserRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@AllArgsConstructor
public class DiamondService {
    private final RedissonClient redissonClient;
    private final DiamondRepository diamondRepository;
    private final DiamondRecordRepository diamondRecordRepository;
    private final UserRepository userRepository;

    private final boolean ORDER_IS_PAID = true;        // The ordered diamond has been paid for
    private final boolean STORAGE_IS_NOT_PAID = false; // Diamonds obtained from storage are not paid for

    @Value("${cheat-use}")
    private final Boolean cheatUse = false; // 금액 치트 사용 여부

    private final int LOCK_WAIT_TIME = 10;      // redis rock wait time
    private final int LOCK_LEASE_TIME = 3;      // redis rock max using time
    private final String LOCK_PRE_FIX = "d:l:"; // diamond lock prefix name d:l:{user_id}

    protected long addDiamondWithLock(final long userId, final long amount, boolean isPaid, String desc, String idempotentKey) {
        checkDuplicatedRequestForDiamond(idempotentKey);
        Diamond diamond = getDiamondElseCreate(userId);   // 다이아몬드 취득
        RLock lock = getUserLock(userId);       // 해당 유저의 코인락
        long total;
        long paidDiamond = 0;
        try {
            // 락획득 시도시 실패했는지 체크
            checkFailureGetLock(lock, userId);
            if (isPaid) {
                diamond.addPaidAmount(amount);
                paidDiamond += amount;
            } else {
                diamond.addAmount(amount);
            }
            total = diamond.getAmount();
            long resultDiamond = diamondRepository.save(diamond).getAmount();
            // record the history of diamond
            diamondRecordRepository.save(DiamondRecord.builder()
                    .userId(userId)
                    .changeType(ChangeType.ADD)
                    .changeDiamond(amount)
                    .paidDiamond(paidDiamond)
                    .resultDiamond(resultDiamond)
                    .idempotentKey(idempotentKey)
                    .changeDesc(desc)
                    .build());
            log.debug("{}", lock.getHoldCount());
            log.debug("diamond add");
        } catch (InterruptedException e) {
            throw new RestException(ErrorCode.COIN_LOCK_FAIL);
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
        return total;
    }

    protected long spendDiamondWithLock(final long userId, final long amount, String desc, String idempotentKey) {
        checkDuplicatedRequestForDiamond(idempotentKey);
        Diamond diamond = diamondRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_DIAMOND_INFO));
        RLock lock = getUserLock(userId);
        long total;
        long beforePaidDiamond = diamond.getPaidAmount();
        try {
            checkFailureGetLock(lock, userId);
            if (diamond.getAmount() < amount) {
                throw new RestException(ErrorCode.NOT_ENOUGH_COIN);
            }
            diamond.deductAmount(amount);

            log.debug("{}", lock.getHoldCount());
            log.debug("diamond deduct");
            long resultDiamond = diamondRepository.save(diamond).getAmount();
            total = resultDiamond;
            // record the history of diamond
            diamondRecordRepository.save(DiamondRecord.builder()
                    .userId(userId)
                    .changeType(ChangeType.USE)
                    .changeDiamond(amount)
                    .paidDiamond(beforePaidDiamond - diamond.getPaidAmount())
                    .resultDiamond(resultDiamond)
                    .changeDesc(desc)
                    .idempotentKey(idempotentKey)
                    .build());
        } catch (InterruptedException e) {
            throw new RestException(ErrorCode.COIN_LOCK_FAIL);
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
        return total;
    }

    private void checkDuplicatedRequestForDiamond(String idempotentKey) {
        if (idempotentKey == null) return;
        if (diamondRecordRepository.existsByIdempotentKey(idempotentKey)) {
            throw new RestException(ErrorCode.DUPLICATED_DIAMOND_REQUEST);
        }
    }

    @Transactional(rollbackFor = Exception.class) //
    public long useDiamondForShopping(long buyerId, long price, String idempotentKey) {
        return spendDiamondWithLock(buyerId, price, DiamondHistoryDesc.USE_DIAMOND_SHOP, idempotentKey);
    }

    // redis 에서 락을 가져온다.
    private RLock getUserLock(long userId) {
        return redissonClient.getLock(LOCK_PRE_FIX + userId);
    }

    // 락을 가져오는데 실패했다면 (락 취득 상태 검사)
    private void checkFailureGetLock(RLock lock, long userId) throws InterruptedException {
        if (!lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
            log.info("user {} lock fail", userId);
            throw new RestException(ErrorCode.COIN_LOCK_FAIL);
        }
    }

    // 코인 가져오기
    public DiamondVo getDiamondVo(long userId) {
        Diamond diamond = getDiamondElseCreate(userId);
        return DiamondVo.builder()
                .userId(userId)
                .amount(diamond.getAmount())
                .paidAmount(diamond.getPaidAmount())
                .build();
    }
    // 유저 다이아몬드를 조회하고 혹시 없으면 생성(에러 방지 코드)
    protected Diamond getDiamondElseCreate(long userId) {
        return diamondRepository.findByUserId(userId)
                .orElseGet(() -> {
                            if (!userRepository.existsByUserId(userId)) {
                                throw new RestException(ErrorCode.USER_NOT_EXIST);
                            }
                            return diamondRepository.save(Diamond.builder()
                                    .userId(userId)
                                    .amount(0L)
                                    .paidAmount(0L)
                                    .build());
                        }
                );
    }

    @Transactional(rollbackFor = Exception.class)
    public void diamondCheat(DiamondCheatParam diamondCheatParam) {
        if (!cheatUse) {
            throw new RestException(ErrorCode.NOT_ALLOWED_CHEAT);
        }
        if (!userRepository.existsByUserId(diamondCheatParam.getUserId())) {
            throw new RestException(ErrorCode.USER_NOT_EXIST);
        }
        Diamond diamond = getDiamondElseCreate(diamondCheatParam.getUserId());
        diamond.changeAmount(diamondCheatParam.getAmount());
        diamond.changePaidAmount(diamondCheatParam.getPaidAmount());
        diamondRepository.save(diamond);
    }

    // 주문 완료시 다이아몬드 생성
    public void addDiamondByOrder(Long userId, Long quantity, String orderIdempotentKey) {
        addDiamondWithLock(userId, quantity, ORDER_IS_PAID, DiamondHistoryDesc.DEPOSIT_DIAMOND_BY_ORDER_PAID, orderIdempotentKey);
    }

    // 주문
    public void addDiamondByReceiveStorage(Long userId, Long quantity, String idempotentKey) {
        addDiamondWithLock(userId, quantity, STORAGE_IS_NOT_PAID, DiamondHistoryDesc.DEPOSIT_DIAMOND_BY_RECEIVE_STORAGE, idempotentKey);
    }

    public void subDiamondBySendStorage(Long userId, Long quantity, String idempotentKey) {
        spendDiamondWithLock(userId, quantity, DiamondHistoryDesc.DEPOSIT_DIAMOND_BY_SEND_STORAGE, idempotentKey);
    }


    @Transactional(rollbackFor = Exception.class)
    public DiamondDepositVo diamondDeposit(DiamondDepositParam diamondDepositParam) {
        long total = addDiamondWithLock(diamondDepositParam.getUserId(), diamondDepositParam.getAmount(), false, DiamondHistoryDesc.DEPOSIT_GAME_DIAMOND, diamondDepositParam.getIdempotentKey());
        return DiamondDepositVo.builder()
                .resultAmount(total)
                .userId(diamondDepositParam.getUserId())
                .amount(diamondDepositParam.getAmount())
                .idempotentKey(diamondDepositParam.getIdempotentKey())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public DiamondWithdrawVo diamondWithdraw(DiamondWithdrawParam diamondWithdrawParam) {
        long total = spendDiamondWithLock(diamondWithdrawParam.getUserId(), diamondWithdrawParam.getAmount(), DiamondHistoryDesc.WITHDRAW_GAME_DIAMOND, diamondWithdrawParam.getIdempotentKey());
        return DiamondWithdrawVo.builder()
                .resultAmount(total)
                .userId(diamondWithdrawParam.getUserId())
                .amount(diamondWithdrawParam.getAmount())
                .idempotentKey(diamondWithdrawParam.getIdempotentKey())
                .build();
    }
}
