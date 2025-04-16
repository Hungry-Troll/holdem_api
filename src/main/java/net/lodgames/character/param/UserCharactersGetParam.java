package net.lodgames.character.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class UserCharactersGetParam extends PagingParam {
    @JsonIgnore
    private Long userId;
}
