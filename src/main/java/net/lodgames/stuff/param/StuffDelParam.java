package net.lodgames.stuff.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StuffDelParam {
    @JsonIgnore
    private Long id; // 물건 고유번호
}
