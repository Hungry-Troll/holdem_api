package net.lodgames.shop.collection.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class CollectionListParam extends PagingParam {
    @JsonIgnore
    private long userId;
}
