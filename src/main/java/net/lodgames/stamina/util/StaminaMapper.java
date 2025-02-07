package net.lodgames.stamina.util;

import net.lodgames.stamina.model.Stamina;
import net.lodgames.stamina.vo.StaminaAcquireVo;
import net.lodgames.stamina.vo.StaminaConsumeVo;
import net.lodgames.stamina.vo.StaminaGetVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StaminaMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StaminaGetVo updateStaminaToVo(Stamina stamina);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StaminaConsumeVo updateStaminaToConsumeVo(Stamina stamina);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StaminaAcquireVo updateStaminaToAcquireVo(Stamina stamina);
}
