package net.lodgames.relation.block.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBlockParam {
    @JsonIgnore
    private Long userId;
    private Long blockUserId;
}
