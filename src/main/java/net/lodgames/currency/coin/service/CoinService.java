package net.lodgames.currency.coin.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.currency.coin.constants.CoinHistoryDesc;
import net.lodgames.currency.coin.model.Coin;
import net.lodgames.currency.coin.model.CoinRecord;
import net.lodgames.currency.coin.param.CoinCheatParam;
import net.lodgames.currency.coin.param.CoinDepositParam;
import net.lodgames.currency.coin.param.CoinWithdrawParam;
import net.lodgames.currency.coin.repository.CoinRecordRepository;
import net.lodgames.currency.coin.repository.CoinRepository;
import net.lodgames.currency.coin.vo.CoinDepositVo;
import net.lodgames.currency.coin.vo.CoinVo;
import net.lodgames.currency.coin.vo.CoinWithdrawVo;
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
public class CoinService {
    private final RedissonClient redissonClient;
    private final CoinRepository coinRepository;
    private final CoinRecordRepository coinRecordRepository;
    private final UserRepository userRepository;

    @Value("${cheat-use}")
    private final Boolean cheatUse = false; // 금액 치트 사용 여부

    private final int LOCK_WAIT_TIME = 10;      // redis rock wait time
    private final int LOCK_LEASE_TIME = 3;      // redis rock max using time
    private final String LOCK_PRE_FIX = "o:l:"; // coin lock prefix name g:l:{user_id}

    // 코인 증가(락 사용)
    protected long addCoinWithLock(final long userId, final long amount, String desc, String idempotentKey) {
        checkDuplicatedRequestForCoin(idempotentKey);
        RLock lock = getUserLock(userId); // 해당 유저의 코인락
        long total;
        try {
            // 락획득 시도시 실패했는지 체크
            checkFailureGetLock(lock, userId);
            Coin coin = coinRepository.findByUserId(userId)
                    .orElse( // 없으면 생성
                            Coin.builder()
                                    .userId(userId)
                                    .amount(0L)
                                    .build());
            coin.addAmount(amount);
            total = coin.getAmount();
            long resultCoin = coinRepository.save(coin).getAmount();
            // record the history of coin
            coinRecordRepository.save(CoinRecord.builder()
                    .userId(userId)
                    .changeType(ChangeType.ADD)
                    .changeCoin(amount)
                    .resultCoin(resultCoin)
                    .idempotentKey(idempotentKey)
                    .changeDesc(desc)
                    .build());
            log.debug("{}", lock.getHoldCount());
            log.debug("coin add");
        } catch (InterruptedException e) {
            throw new RestException(ErrorCode.COIN_LOCK_FAIL);
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
        return total;
    }

    // 코인 소모
    protected long spendCoinWithLock(final long userId, final long amount, String desc, String idempotentKey) {
        checkDuplicatedRequestForCoin(idempotentKey);
        RLock lock = getUserLock(userId);
        long total;
        try {
            checkFailureGetLock(lock, userId);
            Coin coin = coinRepository.findByUserId(userId)
                    .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_COIN_INFO));
            if (coin.getAmount() < amount) {
                throw new RestException(ErrorCode.NOT_ENOUGH_COIN);
            }
            coin.deductAmount(amount);

            log.debug("{}", lock.getHoldCount());
            log.debug("coin deduct");
            long resultCoin = coinRepository.save(coin).getAmount();
            total = resultCoin;
            // record the history of coin
            coinRecordRepository.save(CoinRecord.builder()
                    .userId(userId)
                    .changeType(ChangeType.USE)
                    .changeCoin(amount)
                    .resultCoin(resultCoin)
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

    // 코인 관련 요청 중복 체크 ( 명등키 이용해 확인 )
    private void checkDuplicatedRequestForCoin(String idempotentKey) {
        if (idempotentKey == null) return;
        if (coinRecordRepository.existsByIdempotentKey(idempotentKey)) {
            throw new RestException(ErrorCode.DUPLICATED_COIN_REQUEST);
        }
    }

    @Transactional(rollbackFor = Exception.class) //
    public long useCoinForShopping(long buyerId, long price, String idempotentKey) {
        return spendCoinWithLock(buyerId, price, CoinHistoryDesc.USE_COIN_SHOP, idempotentKey);
    }

    // redis에서 락을 가져온다.
    private RLock getUserLock(long userId) {
        return redissonClient.getLock(LOCK_PRE_FIX + userId);
    }

    // 락을 가져오는데 실패했다면 (락 취득 상태 검사)
    private void checkFailureGetLock(RLock lock, long userId) throws InterruptedException {
        if (!lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
            log.info("user " + userId + " lock fail");
            throw new RestException(ErrorCode.COIN_LOCK_FAIL);
        }
    }

    // 코인 가져오기
    public CoinVo getCoin(long userId) {
        Coin coin = getCoinElseCreate(userId);
        return CoinVo.builder()
                .userId(userId)
                .amount(coin.getAmount())
                .build();
    }

    // 유저 코인을 조회하고 혹시 없으면 생성(에러 방지 코드)
    protected Coin getCoinElseCreate(long userId) {
        return coinRepository.findByUserId(userId)
                .orElseGet(() -> {
                            if (!userRepository.existsByUserId(userId)) {
                                throw new RestException(ErrorCode.USER_NOT_EXIST);
                            }
                            return coinRepository.save(Coin.builder()
                                    .userId(userId)
                                    .amount(0L)
                                    .build());
                        }
                );
    }

    @Transactional(rollbackFor = Exception.class)
    public void coinCheat(CoinCheatParam coinCheatParam) {
        if (!cheatUse) {
            throw new RestException(ErrorCode.NOT_ALLOWED_CHEAT);
        }
        Coin coin = getCoinElseCreate(coinCheatParam.getUserId());
        coin.changeAmount(coinCheatParam.getAmount());
        coinRepository.save(coin);
    }

    @Transactional(rollbackFor = Exception.class)
    public CoinDepositVo coinDeposit(CoinDepositParam coinDepositParam) {
        long total = addCoinWithLock(coinDepositParam.getUserId(), coinDepositParam.getAmount(), CoinHistoryDesc.DEPOSIT_GAME_COIN, coinDepositParam.getIdempotentKey());
        return CoinDepositVo.builder()
                .resultAmount(total)
                .userId(coinDepositParam.getUserId())
                .amount(coinDepositParam.getAmount())
                .idempotentKey(coinDepositParam.getIdempotentKey())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public CoinWithdrawVo coinWithdraw(CoinWithdrawParam coinWithdrawParam) {
        long total = spendCoinWithLock(coinWithdrawParam.getUserId(), coinWithdrawParam.getAmount(), CoinHistoryDesc.WITHDRAW_GAME_COIN, coinWithdrawParam.getIdempotentKey());
        return CoinWithdrawVo.builder()
                .resultAmount(total)
                .userId(coinWithdrawParam.getUserId())
                .amount(coinWithdrawParam.getAmount())
                .idempotentKey(coinWithdrawParam.getIdempotentKey())
                .build();

    }

    public void addCoinByOrder(Long userId, Long quantity, String orderIdempotentKey) {
        addCoinWithLock(userId, quantity, CoinHistoryDesc.DEPOSIT_BY_ORDER_PAID, orderIdempotentKey);
    }

    public void addCoinByBundleTransaction(Long userId, Long quantity) {
        String orderIdempotentKey = UUID.randomUUID().toString();
        addCoinWithLock(userId, quantity, CoinHistoryDesc.DEPOSIT_COIN_BY_BUNDLE_CURRENCY, orderIdempotentKey);
    }

    public void addCoinByReceiveStorage(Long userId, Long quantity, String idempotentKey) {
        addCoinWithLock(userId, quantity, CoinHistoryDesc.DEPOSIT_COIN_BY_RECEIVE_STORAGE, idempotentKey);
    }

    public void subCoinBySendStorage(Long userId, Long quantity, String idempotentKey) {
        spendCoinWithLock(userId, quantity, CoinHistoryDesc.DEPOSIT_COIN_BY_SEND_STORAGE, idempotentKey);
    }

}
