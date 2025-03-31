package net.lodgames.society.util;

import net.lodgames.society.model.Society;
import net.lodgames.society.param.SocietyModParam;
import net.lodgames.society.vo.SocietyInfoVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SocietyMapper {
    @Mapping(target = "joinType", ignore = true)
    @Mapping(target = "passcode", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSocietyFromParam(SocietyModParam param, @MappingTarget Society stuff);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SocietyInfoVo updateSocietyToInfoVo(Society society);
}
