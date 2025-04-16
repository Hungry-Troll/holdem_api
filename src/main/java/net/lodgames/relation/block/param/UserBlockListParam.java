package net.lodgames.relation.block.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBlockListParam extends PagingParam {
    @JsonIgnore
    private Long userId;
}
