package net.lodgames.shop.order.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAcceptParam {
    @JsonIgnore
    private long userId;
    private long productId;
}
