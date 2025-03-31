package net.lodgames.society.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.society.constants.JoinType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SocietyVo {
    private Long id;
    private String name;
    private String image;
    private String backImage;
    private JoinType joinType;
    private String info;
    private List<String> tags;
    @JsonIgnore
    private String tag;
    private LocalDateTime createdAt;
}

