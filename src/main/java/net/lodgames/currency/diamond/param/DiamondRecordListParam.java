package net.lodgames.currency.diamond.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class DiamondRecordListParam extends PagingParam {
    @JsonIgnore
    private long userId;
}
