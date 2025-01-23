package net.lodgames.relation.block.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBlockVo {
    @JsonIgnore
    private long id;
    @JsonIgnore
    private long userId;
    private long blockUserId;
}
