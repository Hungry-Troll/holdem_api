package net.lodgames.relation.memo.param;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMemoGetParam {
    private long userId;
    private long targetUserId;
}
