package net.lodgames.relation.memo.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
public class UserMemoAddParam {
    @JsonIgnore
    private Long targetUserId;
    @JsonIgnore
    private Long userId;
    private String memoText;
    private String tag;
}
