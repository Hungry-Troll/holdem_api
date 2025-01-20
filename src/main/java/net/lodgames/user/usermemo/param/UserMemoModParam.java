package net.lodgames.user.usermemo.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMemoModParam {
    @JsonIgnore
    private long userId;
    @JsonIgnore
    private long targetUserId;
    private String memoText;
    private String tag;
}
