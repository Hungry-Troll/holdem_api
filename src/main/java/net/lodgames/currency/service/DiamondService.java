package net.lodgames.currency.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.currency.constants.ChangeType;
import net.lodgames.currency.constants.DiamondHistoryDesc;
import net.lodgames.currency.model.Diamond;
import net.lodgames.currency.model.DiamondRecord;
import net.lodgames.currency.param.DiamondDepositParam;
import net.lodgames.currency.param.DiamondWithdrawParam;
import net.lodgames.currency.repository.DiamondRecordRepository;
import net.lodgames.currency.repository.DiamondRepository;
import net.lodgames.currency.vo.DiamondDepositVo;
import net.lodgames.currency.vo.DiamondVo;
import net.lodgames.currency.vo.DiamondWithdrawVo;
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

    @Value("${cheat-use}")
    private final Boolean cheatUse = false; // 금액 치트 사용 여부

    private final int LOCK_WAIT_TIME = 10;      // redis rock wait time
    private final int LOCK_LEASE_TIME = 3;      // redis rock max using time
    private final String LOCK_PRE_FIX = "d:l:"; // diamond lock prefix name d:l:{user_id}

    protected long addDiamondWithLock(final long userId, final long amount, String desc, String idempotentKey) {
        checkDuplicatedRequestForDiamond(idempotentKey);
        Diamond diamond = diamondRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_DIAMOND_INFO));
        RLock lock = getUserLock(userId); // 해당 유저의 코인락
        long total;
        try {
            // 락획득 시도시 실패했는지 체크
            checkFailureGetLock(lock, userId);
            diamond.addAmount(amount);
            total = diamond.getAmount();
            long resultDiamond = diamondRepository.save(diamond).getAmount();
            // record the history of diamond
            diamondRecordRepository.save(DiamondRecord.builder()
                    .userId(userId)
                    .changeType(ChangeType.ADD)
                    .changeDiamond(amount)
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
            throw new RestException(ErrorCode.DUPLICATED_COIN_REQUEST);
        }
        ;
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
    public DiamondVo getDiamond(long userId) {
        Diamond diamond = diamondRepository.findByUserId(userId)
                .orElseGet(() -> {
                            if (!userRepository.existsByUserId(userId)) {
                                throw new RestException(ErrorCode.USER_NOT_EXIST);
                            }
                            return diamondRepository.save(Diamond.builder()
                                    .userId(userId)
                                    .amount(0L)
                                    .build());
                        }
                );
        return DiamondVo.builder().userId(userId).amount(diamond.getAmount()).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void diamondCheat(long userId, long amount) {
        if (!cheatUse) {
            throw new RestException(ErrorCode.NOT_ALLOWED_CHEAT);
        }
        if (!userRepository.existsByUserId(userId)) {
            throw new RestException(ErrorCode.USER_NOT_EXIST);
        }
        Diamond diamond = diamondRepository.findByUserId(userId)
                // 없으면 생성
                .orElse(Diamond.builder()
                        .userId(userId)
                        .amount(0L)
                        .build());
        diamond.changeAmount(amount);
        diamondRepository.save(diamond);
    }

    @Transactional(rollbackFor = Exception.class)
    public DiamondDepositVo diamondDeposit(DiamondDepositParam diamondDepositParam) {
        long total = addDiamondWithLock(diamondDepositParam.getUserId(), diamondDepositParam.getAmount(), DiamondHistoryDesc.DEPOSIT_GAME_DIAMOND, diamondDepositParam.getIdempotentKey());
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

    public void addDiamondByOrder(Long userId, Long quantity, String orderIdempotentKey ) {
        addDiamondWithLock(userId, quantity, DiamondHistoryDesc.DEPOSIT_BY_ORDER_PAID, orderIdempotentKey);
    }

    public void addDiamondByReceiveStorage(Long userId, Long quantity, String idempotentKey) {
        addDiamondWithLock(userId, quantity, DiamondHistoryDesc.DEPOSIT_BY_RECEIVE_STORAGE, idempotentKey);
    }

    public void subDiamondBySendStorage(Long userId, Long quantity, String idempotentKey) {
        spendDiamondWithLock(userId, quantity, DiamondHistoryDesc.DEPOSIT_BY_SEND_STORAGE, idempotentKey);
    }
}
