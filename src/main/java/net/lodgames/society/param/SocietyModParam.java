package net.lodgames.society.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.society.constants.JoinType;

import java.util.List;

@Getter
@Setter
@Builder
public class SocietyModParam {
    @JsonIgnore
    private long userId;
    @JsonIgnore
    private long societyId;
    private String name;
    private JoinType joinType;
    private String passcode;
    private List<String> tags;
    @JsonIgnore
    private String tag;
    private String image;
    private String backImage;
    private String info;
}
