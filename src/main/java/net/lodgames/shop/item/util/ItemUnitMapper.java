package net.lodgames.shop.item.util;

import net.lodgames.shop.item.model.ItemUnit;
import net.lodgames.shop.item.vo.ItemUnitVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemUnitMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemUnitVo updateItemUnitToVo(ItemUnit itemUnit);
}
