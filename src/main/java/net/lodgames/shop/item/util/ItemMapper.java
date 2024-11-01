package net.lodgames.shop.item.util;

import net.lodgames.shop.item.model.Item;
import net.lodgames.shop.item.vo.ItemVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemVo updateItemToVo(Item item);
}
