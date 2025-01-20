package net.lodgames.user.userreport.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserReportsVo {
    private Long id;
    private Long reporterId;
    private Long targetUserId;
    private String reason;
}
