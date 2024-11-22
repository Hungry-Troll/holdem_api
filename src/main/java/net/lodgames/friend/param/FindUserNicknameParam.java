package net.lodgames.friend.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class FindUserNicknameParam extends PagingParam {
    @JsonIgnore
    private long userId;
    private String nickname;
}
