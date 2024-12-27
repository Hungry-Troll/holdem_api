package net.lodgames.currency.coin.service;

import lombok.AllArgsConstructor;
import net.lodgames.currency.coin.param.CoinRecordListParam;
import net.lodgames.currency.coin.repository.CoinRecordQueryRepository;
import net.lodgames.currency.coin.vo.CoinRecordVo;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CoinRecordService {
    private final CoinRecordQueryRepository coinRecordQueryRepository;

    public List<CoinRecordVo> getCoinRecords(CoinRecordListParam coinRecordListParam) {
        return coinRecordQueryRepository.findCoinRecords(coinRecordListParam.getUserId(), coinRecordListParam.of());
    }

}
