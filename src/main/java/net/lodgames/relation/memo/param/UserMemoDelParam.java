package net.lodgames.relation.memo.param;

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
