package net.lodgames.character.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCharacterModParam {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long userId;
    private Long characterId;
    private Long customiseId;
    private Integer level;
    private Integer grade;
    private Integer statusIndex;
}
