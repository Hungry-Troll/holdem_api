package net.lodgames.society.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.society.constants.JoinType;

import java.util.List;

@Getter
@Setter
public class SocietySearchVo {
    private Integer societyId;
    private String name;
    private String image;
    private String backImage;
    private String info;
    private JoinType joinType;
    private List<String> tags;
    private String tag;
}
