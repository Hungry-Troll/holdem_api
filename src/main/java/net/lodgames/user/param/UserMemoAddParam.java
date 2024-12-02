package net.lodgames.user.param;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMemoAddParam {
    private long userId;
    private long targetUserId;
    private String memoText;
}
