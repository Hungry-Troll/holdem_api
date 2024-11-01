package net.lodgames.shop.product.param;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;
import net.lodgames.shop.product.constants.ProductStatus;

@Getter
@Setter
public class ProductListParam extends PagingParam {
    private ProductStatus status;
}
