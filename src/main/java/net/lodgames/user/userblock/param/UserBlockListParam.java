package net.lodgames.user.userblock.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBlockListParam extends PagingParam {
    // 유저
    @JsonIgnore
    private long userId;
}
