package net.lodgames.user.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class SearchUserNicknameParam extends PagingParam {
    @JsonIgnore
    private long userId;
    private String nickname;
}
