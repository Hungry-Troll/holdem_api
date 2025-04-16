package net.lodgames.stamina.util;

import net.lodgames.stamina.model.Stamina;
import net.lodgames.stamina.vo.StaminaVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StaminaMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StaminaVo updateStaminaToVo(Stamina stamina);
}
