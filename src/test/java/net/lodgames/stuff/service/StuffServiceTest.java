package net.lodgames.stuff.service;

import net.lodgames.stuff.constants.StuffStatus;
import net.lodgames.stuff.mapper.StuffMapper;
import net.lodgames.stuff.modle.Stuff;
import net.lodgames.stuff.param.StuffAddParam;
import net.lodgames.stuff.param.StuffListParam;
import net.lodgames.stuff.param.StuffModParam;
import net.lodgames.stuff.repository.StuffQueryRepository;
import net.lodgames.stuff.repository.StuffRepository;
import net.lodgames.stuff.vo.StuffVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("StuffServiceTestTest")
class StuffServiceTest {
    @InjectMocks
    StuffService stuffService;
    @Mock
    private StuffRepository stuffRepository;

    @Mock
    private StuffQueryRepository stuffQueryRepository;

    @Spy
    StuffMapper stuffMapper = Mappers.getMapper(StuffMapper.class); // 매핑 클래스 null point exception 회피

    private Stuff makeDummyStuff() {
        Stuff stuff = Stuff.builder()
                .name("name")
                .status(StuffStatus.STANDBY)
                .description("blah blah blah")
                .makeDatetime(LocalDateTime.now())
                .build();
        stuff.setId(1L);
        stuff.setCreatedAt(LocalDateTime.now());
        stuff.setUpdatedAt(LocalDateTime.now());
        return stuff;
    }

    @Test
    void call_getStuff_then_return_stuff() {
        Stuff stuff = makeDummyStuff();
        when(stuffRepository.findById(anyLong())).thenReturn(Optional.of(stuff));
        StuffVo stuffVo = stuffService.getStuff(anyLong());
        Assertions.assertEquals(stuffVo.getName(), stuff.getName());
        Assertions.assertEquals(stuffVo.getDescription(), stuff.getDescription());
        Assertions.assertEquals(stuffVo.getStatus(), stuff.getStatus());
        Assertions.assertEquals(stuffVo.getMakeDatetime(), stuff.getMakeDatetime());
    }

    @Test
    void call_addStuff_then_stuff_added() {
        Stuff stuff = makeDummyStuff();
        StuffAddParam stuffAddParam = new StuffAddParam();
        when(stuffRepository.save(any(Stuff.class))).thenReturn(stuff);
        StuffVo stuffVo = stuffService.addStuff(stuffAddParam);
        Assertions.assertEquals(stuffVo.getName(), stuff.getName());
        Assertions.assertEquals(stuffVo.getDescription(), stuff.getDescription());
        Assertions.assertEquals(stuffVo.getStatus(), stuff.getStatus());
        Assertions.assertEquals(stuffVo.getMakeDatetime(), stuff.getMakeDatetime());
    }

    @Test
    void call_modStuff_then_stuff_modified() {
        Stuff stuff = makeDummyStuff();
        StuffModParam stuffModParam = new StuffModParam();
        stuffModParam.setId(1L);
        when(stuffRepository.findById(anyLong())).thenReturn(Optional.of(stuff));
        when(stuffRepository.save(any(Stuff.class))).thenReturn(stuff);
        StuffVo stuffVo = stuffService.modStuff(stuffModParam);
        Assertions.assertEquals(stuffVo.getName(), stuff.getName());
        Assertions.assertEquals(stuffVo.getDescription(), stuff.getDescription());
        Assertions.assertEquals(stuffVo.getStatus(), stuff.getStatus());
        Assertions.assertEquals(stuffVo.getMakeDatetime(), stuff.getMakeDatetime());
    }

    @Test
    void call_getStuffList_then_return_stuff_list() {
        Stuff stuff = makeDummyStuff();
        StuffVo stuffVo = stuffMapper.updateStuffToVo(stuff);
        Stuff stuff2 = Stuff.builder()
                .name("name2")
                .status(StuffStatus.STANDBY)
                .description("blah blah blah2")
                .makeDatetime(LocalDateTime.now())
                .build();
        stuff.setId(2L);
        stuff.setCreatedAt(LocalDateTime.now());
        stuff.setUpdatedAt(LocalDateTime.now());
        StuffVo stuffVo2 = stuffMapper.updateStuffToVo(stuff2);
        List<StuffVo> stuffList = new ArrayList<>();
        stuffList.add(stuffVo);
        stuffList.add(stuffVo2);
        when(stuffQueryRepository.getStuffListByCondition(any(StuffListParam.class), any(PageRequest.class))).thenReturn(stuffList);
        StuffListParam stuffListParam = new StuffListParam();
        List<StuffVo> stuffVoList = stuffService.getStuffList(stuffListParam);
        Assertions.assertEquals(stuffVoList.get(0).getName(), stuff.getName());
        Assertions.assertEquals(stuffVoList.get(0).getDescription(), stuff.getDescription());
        Assertions.assertEquals(stuffVoList.get(0).getStatus(), stuff.getStatus());
        Assertions.assertEquals(stuffVoList.get(0).getMakeDatetime(), stuff.getMakeDatetime());
        Assertions.assertEquals(stuffVoList.get(1).getName(), stuff2.getName());
        Assertions.assertEquals(stuffVoList.get(1).getDescription(), stuff2.getDescription());
        Assertions.assertEquals(stuffVoList.get(1).getStatus(), stuff2.getStatus());
        Assertions.assertEquals(stuffVoList.get(1).getMakeDatetime(), stuff2.getMakeDatetime());
    }

    @Test
    void call_retrieveStuff_then_get_stuff_() {
        Stuff stuff = makeDummyStuff();
        when(stuffRepository.findById(anyLong())).thenReturn(Optional.of(stuff));
        Stuff returnStuff = stuffService.retrieveStuff(1L);
        Assertions.assertEquals(returnStuff.getName(), stuff.getName());
        Assertions.assertEquals(returnStuff.getDescription(), stuff.getDescription());
        Assertions.assertEquals(returnStuff.getStatus(), stuff.getStatus());
        Assertions.assertEquals(returnStuff.getMakeDatetime(), stuff.getMakeDatetime());
    }



}