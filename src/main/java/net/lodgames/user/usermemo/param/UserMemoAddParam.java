package net.lodgames.user.usermemo.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMemoAddParam {
    @JsonIgnore
    private long targetUserId;
    @JsonIgnore
    private long userId;
    private String memoText;
    private String tag;
}
