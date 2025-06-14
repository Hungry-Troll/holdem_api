package net.lodgames.currency.common.service;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.currency.gold.model.Gold;
import net.lodgames.currency.gold.repository.GoldRepository;
import net.lodgames.currency.coin.model.Coin;
import net.lodgames.currency.coin.repository.CoinRepository;
import net.lodgames.currency.common.repository.CurrencyQueryRepository;
import net.lodgames.currency.common.vo.CurrencyVo;
import net.lodgames.currency.diamond.model.Diamond;
import net.lodgames.currency.diamond.repository.DiamondRepository;
import net.lodgames.user.constants.Os;
import net.lodgames.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyService {
    private final UserRepository userRepository;
    private final CurrencyQueryRepository currencyQueryRepository;
    private final CoinRepository coinRepository;
    private final DiamondRepository diamondRepository;
    private final GoldRepository goldRepository;

    // 모든 재화 정보를 조회한다.
    public CurrencyVo getCurrencies(long userId, Os os) {
        if (!userRepository.existsByUserId(userId)) {
            throw new RestException(ErrorCode.USER_NOT_EXIST);
        }
        CurrencyVo currencyVo = currencyQueryRepository.findCurrenciesByUserId(userId);
        if (currencyVo != null) {
            currencyVo.setUserId(userId);
            currencyVo.setOs(os);
            return currencyVo;
        } else {
            Coin coin = coinRepository.findByUserId(userId)
                    .orElseGet(() -> coinRepository.save(Coin.builder()
                            .userId(userId)
                            .amount(0L)
                            .build())
                    );
            Diamond diamond = diamondRepository.findByUserId(userId)
                    .orElseGet(() -> diamondRepository.save(Diamond.builder()
                            .userId(userId)
                            .freeAmount(0L)
                            .androidAmount(0L)
                            .iosAmount(0L)
                            .paidAmount(0L)
                            .build())
                    );
            Gold gold = goldRepository.findByUserId(userId)
                    .orElseGet(() -> goldRepository.save(Gold.builder()
                            .userId(userId)
                            .amount(0L)
                            .build())
                    );
            return CurrencyVo.builder()
                    .userId(userId)
                    .goldAmount(gold.getAmount())
                    .coinAmount(coin.getAmount())
                    .diamondAmount(diamond.getTotalAmount(os))
                    .build();
        }
    }

    // 금액 초기화
    public void initCurrencies(long userId) {
        if(!coinRepository.existsByUserId(userId)) {
            coinRepository.save(Coin.builder()
                    .userId(userId)
                    .amount(0L)
                    .build());
        }
        if(!diamondRepository.existsByUserId(userId)) {
            diamondRepository.save(Diamond.builder()
                    .userId(userId)
                    .freeAmount(0L)
                    .androidAmount(0L)
                    .iosAmount(0L)
                    .paidAmount(0L)
                    .build());
        }
        if(!goldRepository.existsByUserId(userId)) {
            goldRepository.save(Gold.builder()
                    .userId(userId)
                    .amount(0L)
                    .build());
        }
    }
}
