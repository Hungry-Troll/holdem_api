package net.lodgames.shop.order.util;

import net.lodgames.shop.order.model.Orders;
import net.lodgames.shop.order.vo.OrderVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderVo updateOrderToVo(Orders orders);
}
