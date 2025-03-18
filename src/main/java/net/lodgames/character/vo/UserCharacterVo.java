package net.lodgames.character.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCharacterVo {
    @JsonIgnore
    private long id;
    @JsonIgnore
    private long userId;
    private long characterId;
    private long customiseId;
    private int level;
    private int grade;
    private int statusIndex;
}
