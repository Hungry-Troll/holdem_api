package net.lodgames.society.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SocietyInfoParam {
    private long userId;
    private long societyId;
}
