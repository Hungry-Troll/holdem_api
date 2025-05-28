package net.lodgames.currency.gold.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.currency.gold.constants.GoldHistoryDesc;
import net.lodgames.currency.gold.model.Gold;
import net.lodgames.currency.gold.model.GoldRecord;
import net.lodgames.currency.gold.param.GoldCheatParam;
import net.lodgames.currency.gold.param.GoldDepositParam;
import net.lodgames.currency.gold.param.GoldWithdrawParam;
import net.lodgames.currency.gold.repository.GoldRecordRepository;
import net.lodgames.currency.gold.repository.GoldRepository;
import net.lodgames.currency.gold.vo.GoldDepositVo;
import net.lodgames.currency.gold.vo.GoldVo;
import net.lodgames.currency.gold.vo.GoldWithdrawVo;
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
public class GoldService {
    private final RedissonClient redissonClient;
    private final GoldRepository goldRepository;
    private final GoldRecordRepository goldRecordRepository;
    private final UserRepository userRepository;

    @Value("${cheat-use}")
    private final Boolean cheatUse = false; // 금액 치트 사용 여부

    private final int LOCK_WAIT_TIME = 10;      // redis rock wait time
    private final int LOCK_LEASE_TIME = 3;      // redis rock max using time
    private final String LOCK_PRE_FIX = "h:l:"; // gold lock prefix name g:l:{user_id}

    // 코인 증가(락 사용)
    protected long addGoldWithLock(final long userId, final long amount, String desc, String idempotentKey) {
        checkDuplicatedRequestForGold(idempotentKey);
        RLock lock = getUserLock(userId); // 해당 유저의 코인락
        long total;
        try {
            // 락획득 시도시 실패했는지 체크
            checkFailureGetLock(lock, userId);
            Gold gold = goldRepository.findByUserId(userId)
                    .orElse( // 없으면 생성
                            Gold.builder()
                                    .userId(userId)
                                    .amount(0L)
                                    .build());
            gold.addAmount(amount);
            total = gold.getAmount();
            long resultGold = goldRepository.save(gold).getAmount();
            // record the history of gold
            goldRecordRepository.save(GoldRecord.builder()
                    .userId(userId)
                    .changeType(ChangeType.ADD)
                    .changeGold(amount)
                    .resultGold(resultGold)
                    .idempotentKey(idempotentKey)
                    .changeDesc(desc)
                    .build());
            log.debug("{}", lock.getHoldCount());
            log.debug("gold add");
        } catch (InterruptedException e) {
            throw new RestException(ErrorCode.GOLD_LOCK_FAIL);
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
        return total;
    }

    // 코인 소모
    protected long spendGoldWithLock(final long userId, final long amount, String desc, String idempotentKey) {
        checkDuplicatedRequestForGold(idempotentKey);
        RLock lock = getUserLock(userId);
        long total;
        try {
            checkFailureGetLock(lock, userId);
            Gold gold = goldRepository.findByUserId(userId)
                    .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_GOLD_INFO));
            if (gold.getAmount() < amount) {
                throw new RestException(ErrorCode.NOT_ENOUGH_GOLD);
            }
            gold.deductAmount(amount);

            log.debug("{}", lock.getHoldCount());
            log.debug("gold deduct");
            long resultGold = goldRepository.save(gold).getAmount();
            total = resultGold;
            // record the history of gold
            goldRecordRepository.save(GoldRecord.builder()
                    .userId(userId)
                    .changeType(ChangeType.USE)
                    .changeGold(amount)
                    .resultGold(resultGold)
                    .changeDesc(desc)
                    .idempotentKey(idempotentKey)
                    .build());
        } catch (InterruptedException e) {
            throw new RestException(ErrorCode.GOLD_LOCK_FAIL);
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
        return total;
    }

    // 코인 관련 요청 중복 체크 ( 명등키 이용해 확인 )
    private void checkDuplicatedRequestForGold(String idempotentKey) {
        if (idempotentKey == null) return;
        if (goldRecordRepository.existsByIdempotentKey(idempotentKey)) {
            throw new RestException(ErrorCode.DUPLICATED_GOLD_REQUEST);
        }
    }

    @Transactional(rollbackFor = Exception.class) //
    public long useGoldForShopping(long buyerId, long price, String idempotentKey) {
        return spendGoldWithLock(buyerId, price, GoldHistoryDesc.USE_GOLD_SHOP, idempotentKey);
    }

    // redis에서 락을 가져온다.
    private RLock getUserLock(long userId) {
        return redissonClient.getLock(LOCK_PRE_FIX + userId);
    }

    // 락을 가져오는데 실패했다면 (락 취득 상태 검사)
    private void checkFailureGetLock(RLock lock, long userId) throws InterruptedException {
        if (!lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
            log.info("user " + userId + " lock fail");
            throw new RestException(ErrorCode.GOLD_LOCK_FAIL);
        }
    }

    // 코인 가져오기
    public GoldVo getGold(long userId) {
        Gold gold = getGoldElseCreate(userId);
        return GoldVo.builder().userId(userId).amount(gold.getAmount()).build();
    }

    public Gold getGoldElseCreate(long userId) {
        return goldRepository.findByUserId(userId)
                .orElseGet(() -> {
                            if (!userRepository.existsByUserId(userId)) {
                                throw new RestException(ErrorCode.USER_NOT_EXIST);
                            }
                            return goldRepository.save(Gold.builder()
                                    .userId(userId)
                                    .amount(0L)
                                    .build());
                        }
                );
    }

    @Transactional(rollbackFor = Exception.class)
    public void goldCheat(GoldCheatParam goldCheatParam) {
        if (!cheatUse) {
            throw new RestException(ErrorCode.NOT_ALLOWED_CHEAT);
        }
        Gold gold = getGoldElseCreate(goldCheatParam.getUserId());
        gold.changeAmount(goldCheatParam.getAmount());
        goldRepository.save(gold);
    }

    @Transactional(rollbackFor = Exception.class)
    public GoldDepositVo goldDeposit(GoldDepositParam goldDepositParam) {
        long total = addGoldWithLock(goldDepositParam.getUserId(), goldDepositParam.getAmount(), GoldHistoryDesc.DEPOSIT_GAME_GOLD, goldDepositParam.getIdempotentKey());
        return GoldDepositVo.builder()
                .resultAmount(total)
                .userId(goldDepositParam.getUserId())
                .amount(goldDepositParam.getAmount())
                .idempotentKey(goldDepositParam.getIdempotentKey())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public GoldWithdrawVo goldWithdraw(GoldWithdrawParam goldWithdrawParam) {
        long total = spendGoldWithLock(goldWithdrawParam.getUserId(), goldWithdrawParam.getAmount(), GoldHistoryDesc.WITHDRAW_GAME_GOLD, goldWithdrawParam.getIdempotentKey());
        return GoldWithdrawVo.builder()
                .resultAmount(total)
                .userId(goldWithdrawParam.getUserId())
                .amount(goldWithdrawParam.getAmount())
                .idempotentKey(goldWithdrawParam.getIdempotentKey())
                .build();

    }

    public void addGoldByBundleTransaction(Long userId, Long quantity) {
        String orderIdempotentKey = UUID.randomUUID().toString();
        addGoldWithLock(userId, quantity, GoldHistoryDesc.DEPOSIT_GOLD_BY_BUNDLE_CURRENCY, orderIdempotentKey);
    }

    public void addGoldByReceiveStorage(Long userId, Long quantity, String idempotentKey) {
        addGoldWithLock(userId, quantity, GoldHistoryDesc.DEPOSIT_GOLD_BY_RECEIVE_STORAGE, idempotentKey);
    }

    public void subGoldBySendStorage(Long userId, Long quantity, String idempotentKey) {
        spendGoldWithLock(userId, quantity, GoldHistoryDesc.DEPOSIT_GOLD_BY_SEND_STORAGE, idempotentKey);
    }

}
