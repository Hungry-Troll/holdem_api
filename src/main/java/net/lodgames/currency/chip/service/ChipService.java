package net.lodgames.currency.chip.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.currency.chip.constants.ChipHistoryDesc;
import net.lodgames.currency.chip.model.Chip;
import net.lodgames.currency.chip.model.ChipRecord;
import net.lodgames.currency.chip.param.ChipCheatParam;
import net.lodgames.currency.chip.param.ChipDepositParam;
import net.lodgames.currency.chip.param.ChipWithdrawParam;
import net.lodgames.currency.chip.repository.ChipRecordRepository;
import net.lodgames.currency.chip.repository.ChipRepository;
import net.lodgames.currency.chip.vo.ChipDepositVo;
import net.lodgames.currency.chip.vo.ChipVo;
import net.lodgames.currency.chip.vo.ChipWithdrawVo;
import net.lodgames.user.repository.UserRepository;
import net.lodgames.currency.common.constants.ChangeType;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@AllArgsConstructor
public class ChipService {
    private final RedissonClient redissonClient;
    private final ChipRepository chipRepository;
    private final ChipRecordRepository chipRecordRepository;
    private final UserRepository userRepository;

    @Value("${cheat-use}")
    private final Boolean cheatUse = false; // 금액 치트 사용 여부

    private final int LOCK_WAIT_TIME = 10;      // redis rock wait time
    private final int LOCK_LEASE_TIME = 3;      // redis rock max using time
    private final String LOCK_PRE_FIX = "h:l:"; // chip lock prefix name g:l:{user_id}

    // 코인 증가(락 사용)
    protected long addChipWithLock(final long userId, final long amount, String desc, String idempotentKey) {
        checkDuplicatedRequestForChip(idempotentKey);
        RLock lock = getUserLock(userId); // 해당 유저의 코인락
        long total;
        try {
            // 락획득 시도시 실패했는지 체크
            checkFailureGetLock(lock, userId);
            Chip chip = chipRepository.findByUserId(userId)
                    .orElse( // 없으면 생성
                            Chip.builder()
                                    .userId(userId)
                                    .amount(0L)
                                    .build());
            chip.addAmount(amount);
            total = chip.getAmount();
            long resultChip = chipRepository.save(chip).getAmount();
            // record the history of chip
            chipRecordRepository.save(ChipRecord.builder()
                    .userId(userId)
                    .changeType(ChangeType.ADD)
                    .changeChip(amount)
                    .resultChip(resultChip)
                    .idempotentKey(idempotentKey)
                    .changeDesc(desc)
                    .build());
            log.debug("{}", lock.getHoldCount());
            log.debug("chip add");
        } catch (InterruptedException e) {
            throw new RestException(ErrorCode.CHIP_LOCK_FAIL);
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
        return total;
    }

    // 코인 소모
    protected long spendChipWithLock(final long userId, final long amount, String desc, String idempotentKey) {
        checkDuplicatedRequestForChip(idempotentKey);
        RLock lock = getUserLock(userId);
        long total;
        try {
            checkFailureGetLock(lock, userId);
            Chip chip = chipRepository.findByUserId(userId)
                    .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_CHIP_INFO));
            if (chip.getAmount() < amount) {
                throw new RestException(ErrorCode.NOT_ENOUGH_CHIP);
            }
            chip.deductAmount(amount);

            log.debug("{}", lock.getHoldCount());
            log.debug("chip deduct");
            long resultChip = chipRepository.save(chip).getAmount();
            total = resultChip;
            // record the history of chip
            chipRecordRepository.save(ChipRecord.builder()
                    .userId(userId)
                    .changeType(ChangeType.USE)
                    .changeChip(amount)
                    .resultChip(resultChip)
                    .changeDesc(desc)
                    .idempotentKey(idempotentKey)
                    .build());
        } catch (InterruptedException e) {
            throw new RestException(ErrorCode.CHIP_LOCK_FAIL);
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
        return total;
    }

    // 코인 관련 요청 중복 체크 ( 명등키 이용해 확인 )
    private void checkDuplicatedRequestForChip(String idempotentKey) {
        if (idempotentKey == null) return;
        if (chipRecordRepository.existsByIdempotentKey(idempotentKey)) {
            throw new RestException(ErrorCode.DUPLICATED_CHIP_REQUEST);
        }
    }

    @Transactional(rollbackFor = Exception.class) //
    public long useChipForShopping(long buyerId, long price, String idempotentKey) {
        return spendChipWithLock(buyerId, price, ChipHistoryDesc.USE_CHIP_SHOP, idempotentKey);
    }

    // redis에서 락을 가져온다.
    private RLock getUserLock(long userId) {
        return redissonClient.getLock(LOCK_PRE_FIX + userId);
    }

    // 락을 가져오는데 실패했다면 (락 취득 상태 검사)
    private void checkFailureGetLock(RLock lock, long userId) throws InterruptedException {
        if (!lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
            log.info("user " + userId + " lock fail");
            throw new RestException(ErrorCode.CHIP_LOCK_FAIL);
        }
    }

    // 코인 가져오기
    public ChipVo getChip(long userId) {
        Chip chip = getChipElseCreate(userId);
        return ChipVo.builder().userId(userId).amount(chip.getAmount()).build();
    }

    public Chip getChipElseCreate(long userId) {
        return chipRepository.findByUserId(userId)
                .orElseGet(() -> {
                            if (!userRepository.existsByUserId(userId)) {
                                throw new RestException(ErrorCode.USER_NOT_EXIST);
                            }
                            return chipRepository.save(Chip.builder()
                                    .userId(userId)
                                    .amount(0L)
                                    .build());
                        }
                );
    }

    @Transactional(rollbackFor = Exception.class)
    public void chipCheat(ChipCheatParam chipCheatParam) {
        if (!cheatUse) {
            throw new RestException(ErrorCode.NOT_ALLOWED_CHEAT);
        }
        Chip chip = getChipElseCreate(chipCheatParam.getUserId());
        chip.changeAmount(chipCheatParam.getAmount());
        chipRepository.save(chip);
    }

    @Transactional(rollbackFor = Exception.class)
    public ChipDepositVo chipDeposit(ChipDepositParam chipDepositParam) {
        long total = addChipWithLock(chipDepositParam.getUserId(), chipDepositParam.getAmount(), ChipHistoryDesc.DEPOSIT_GAME_CHIP, chipDepositParam.getIdempotentKey());
        return ChipDepositVo.builder()
                .resultAmount(total)
                .userId(chipDepositParam.getUserId())
                .amount(chipDepositParam.getAmount())
                .idempotentKey(chipDepositParam.getIdempotentKey())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public ChipWithdrawVo chipWithdraw(ChipWithdrawParam chipWithdrawParam) {
        long total = spendChipWithLock(chipWithdrawParam.getUserId(), chipWithdrawParam.getAmount(), ChipHistoryDesc.WITHDRAW_GAME_CHIP, chipWithdrawParam.getIdempotentKey());
        return ChipWithdrawVo.builder()
                .resultAmount(total)
                .userId(chipWithdrawParam.getUserId())
                .amount(chipWithdrawParam.getAmount())
                .idempotentKey(chipWithdrawParam.getIdempotentKey())
                .build();

    }

    public void addChipByBundleTransaction(Long userId, Long quantity) {
        String orderIdempotentKey = UUID.randomUUID().toString();
        addChipWithLock(userId, quantity, ChipHistoryDesc.DEPOSIT_CHIP_BY_BUNDLE_CURRENCY, orderIdempotentKey);
    }

    public void addChipByReceiveStorage(Long userId, Long quantity, String idempotentKey) {
        addChipWithLock(userId, quantity, ChipHistoryDesc.DEPOSIT_CHIP_BY_RECEIVE_STORAGE, idempotentKey);
    }

    public void subChipBySendStorage(Long userId, Long quantity, String idempotentKey) {
        spendChipWithLock(userId, quantity, ChipHistoryDesc.DEPOSIT_CHIP_BY_SEND_STORAGE, idempotentKey);
    }

}
