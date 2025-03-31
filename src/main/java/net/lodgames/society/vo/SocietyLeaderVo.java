package net.lodgames.society.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.society.constants.JoinType;

@Getter
@Setter
public class SocietyLeaderVo {
    private JoinType joinType;
    private String passcode;
    private long userId;
}