package net.lodgames.relation.memo.param;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMemoDelParam {
    private Long userId;
    private Long targetUserId;
}
