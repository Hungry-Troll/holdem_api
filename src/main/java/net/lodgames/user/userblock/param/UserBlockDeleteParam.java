package net.lodgames.user.userblock.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBlockDeleteParam {
    @JsonIgnore
    private Long userId;
    private Long blockUserId;
}
