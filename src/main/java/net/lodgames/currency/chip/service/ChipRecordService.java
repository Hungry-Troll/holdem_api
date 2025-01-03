package net.lodgames.currency.chip.service;

import lombok.AllArgsConstructor;
import net.lodgames.currency.chip.param.ChipRecordListParam;
import net.lodgames.currency.chip.repository.ChipRecordQueryRepository;
import net.lodgames.currency.chip.vo.ChipRecordVo;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ChipRecordService {
    private final ChipRecordQueryRepository chipRecordQueryRepository;

    public List<ChipRecordVo> getChipRecords(ChipRecordListParam chipRecordListParam) {
        return chipRecordQueryRepository.findChipRecords(chipRecordListParam.getUserId(), chipRecordListParam.of());
    }

}
