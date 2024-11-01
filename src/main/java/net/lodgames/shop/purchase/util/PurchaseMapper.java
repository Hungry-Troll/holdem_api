package net.lodgames.shop.purchase.util;

import net.lodgames.shop.purchase.model.Purchase;
import net.lodgames.shop.purchase.vo.PurchaseVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PurchaseMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PurchaseVo updatePurchaseToVo(Purchase purchase);
}
