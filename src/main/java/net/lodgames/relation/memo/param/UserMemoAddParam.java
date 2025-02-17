package net.lodgames.relation.memo.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
public class UserMemoAddParam {
    @JsonIgnore
    private long targetUserId;
    @JsonIgnore
    private long userId;
    private String memoText;
    private String tag;
}
