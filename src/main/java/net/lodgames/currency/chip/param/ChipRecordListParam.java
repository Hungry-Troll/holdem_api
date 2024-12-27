package net.lodgames.currency.chip.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class ChipRecordListParam extends PagingParam {
    @JsonIgnore
    private long userId;
}
