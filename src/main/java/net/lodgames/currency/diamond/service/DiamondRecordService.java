package net.lodgames.currency.diamond.service;

import lombok.AllArgsConstructor;
import net.lodgames.currency.diamond.param.DiamondRecordListParam;
import net.lodgames.currency.diamond.repository.DiamondRecordQueryRepository;
import net.lodgames.currency.diamond.vo.DiamondRecordVo;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DiamondRecordService {
    private final DiamondRecordQueryRepository diamondRecordQueryRepository;

    public List<DiamondRecordVo> getDiamondRecords(DiamondRecordListParam diamondRecordListParam) {
        return diamondRecordQueryRepository.findDiamondRecords(diamondRecordListParam.getUserId(), diamondRecordListParam.of());
    }

}
