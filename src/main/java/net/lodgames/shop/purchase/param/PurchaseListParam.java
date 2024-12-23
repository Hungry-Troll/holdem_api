package net.lodgames.shop.purchase.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class PurchaseListParam extends PagingParam {
    @JsonIgnore
    private long userId;
}
