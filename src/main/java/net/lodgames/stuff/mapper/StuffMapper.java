package net.lodgames.stuff.mapper;

import net.lodgames.stuff.modle.Stuff;
import net.lodgames.stuff.param.StuffModParam;
import net.lodgames.stuff.vo.StuffVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StuffMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStuffFromParam(StuffModParam param, @MappingTarget Stuff stuff);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StuffVo updateStuffToVo(Stuff stuff);
}
