package net.lodgames.shop.item.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemParam {
    @JsonIgnore
    private long userId;
    private Long itemId;
}
