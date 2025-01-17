package net.lodgames.user.userreport.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModUserReportParam {
    private Long reporterId; 
    private Long targetUserId;
    private String reason;
    private String screenshot;
}
