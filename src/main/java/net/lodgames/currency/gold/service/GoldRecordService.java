package net.lodgames.currency.gold.service;

import lombok.AllArgsConstructor;
import net.lodgames.currency.gold.param.GoldRecordListParam;
import net.lodgames.currency.gold.repository.GoldRecordQueryRepository;
import net.lodgames.currency.gold.vo.GoldRecordVo;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GoldRecordService {
    private final GoldRecordQueryRepository goldRecordQueryRepository;

    public List<GoldRecordVo> getGoldRecords(GoldRecordListParam goldRecordListParam) {
        return goldRecordQueryRepository.findGoldRecords(goldRecordListParam.getUserId(), goldRecordListParam.of());
    }

}
