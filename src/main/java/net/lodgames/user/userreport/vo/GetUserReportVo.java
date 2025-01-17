package net.lodgames.user.userreport.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.user.userreport.constants.UserReportStatus;

@Getter
@Setter
public class GetUserReportVo {
    @JsonIgnore
    private Long id;
    private Long reporterId;
    private Long targetUserId;
    private String reason;
    private UserReportStatus status;
}
