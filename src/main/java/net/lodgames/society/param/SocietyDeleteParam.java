package net.lodgames.society.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SocietyDeleteParam {
    @JsonIgnore
    private long userId;
    @JsonIgnore
    private long societyId;
}
