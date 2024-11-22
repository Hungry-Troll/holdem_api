package net.lodgames.user.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.lodgames.common.param.PagingParam;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBlockListParam extends PagingParam {
    // 유저
    @JsonIgnore
    private long userId;
}
