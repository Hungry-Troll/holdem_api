package net.lodgames.user.usermemo.param;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMemoDelParam {
    private long userId;
    private long targetUserId;
}
