package net.lodgames.dictionary.userCharacter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCharacterUpdateVo {
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
