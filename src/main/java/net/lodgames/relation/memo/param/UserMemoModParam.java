package net.lodgames.relation.memo.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMemoModParam {
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private Long targetUserId;
    private String memoText;
    private String tag;
}
