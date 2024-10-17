package net.lodgames.stuff.service;

import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.stuff.mapper.StuffMapper;
import net.lodgames.stuff.modle.Stuff;
import net.lodgames.stuff.param.StuffAddParam;
import net.lodgames.stuff.param.StuffDelParam;
import net.lodgames.stuff.param.StuffListParam;
import net.lodgames.stuff.param.StuffModParam;
import net.lodgames.stuff.repository.StuffQueryRepository;
import net.lodgames.stuff.repository.StuffRepository;
import net.lodgames.stuff.vo.StuffVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class StuffService {
    private final StuffQueryRepository stuffQueryRepository;
    private final StuffRepository stuffRepository;
    private final StuffMapper stuffMapper;

    @Transactional(readOnly = true)
    public StuffVo getStuff(Long stuffId) {
        return stuffMapper.updateStuffToVo(retrieveStuff(stuffId));
    }

    @Transactional(rollbackFor = Exception.class)
    public StuffVo addStuff(StuffAddParam stuffAddParam) {
         Stuff stuff = stuffRepository.save(Stuff.builder()
                .name(stuffAddParam.getName())
                .status(stuffAddParam.getStatus())
                .description(stuffAddParam.getDescription())
                .makeDatetime(stuffAddParam.getMakeDatetime()).build());
         return stuffMapper.updateStuffToVo(stuff);
    }

    @Transactional(rollbackFor = Exception.class)
    public StuffVo modStuff(StuffModParam stuffModParam) {
        Stuff stuff = retrieveStuff(stuffModParam.getId());
        stuffMapper.updateStuffFromParam(stuffModParam , stuff);
        return stuffMapper.updateStuffToVo(stuffRepository.save(stuff));
    }

    public List<StuffVo> getStuffList(StuffListParam stuffListParam) {
        return stuffQueryRepository.getStuffListByCondition(stuffListParam, stuffListParam.of());
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteStuff(StuffDelParam stuffDelParam) {
        stuffRepository.delete(retrieveStuff(stuffDelParam.getId()));
    }

    // Stuff 가져오기
    protected Stuff retrieveStuff(long stuffId) {
        return stuffRepository.findById(stuffId).orElseThrow(() ->
                new RestException(ErrorCode.STUFF_NOT_EXIST)
        );
    }
}
