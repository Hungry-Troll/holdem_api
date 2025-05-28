package net.lodgames.currency.gold.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class GoldRecordListParam extends PagingParam {
    @JsonIgnore
    private long userId;
}
