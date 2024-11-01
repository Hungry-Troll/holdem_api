package net.lodgames.shop.bundle.util;

import net.lodgames.shop.bundle.model.Bundle;
import net.lodgames.shop.bundle.vo.BundleVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BundleMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BundleVo updateBundleToVo(Bundle bundle);
}
