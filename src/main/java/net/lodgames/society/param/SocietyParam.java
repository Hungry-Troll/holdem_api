package net.lodgames.society.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.society.constants.JoinType;

import java.util.List;

@Getter
@Setter
public class SocietyParam {
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
