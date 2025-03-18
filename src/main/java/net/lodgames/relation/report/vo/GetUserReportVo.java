package net.lodgames.relation.report.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.relation.report.constants.UserReportStatus;

@Getter
@Setter
public class GetUserReportVo {
    @JsonIgnore
    private long id;
    private long reporterId;
    private long targetUserId;
    private String reason;
    private UserReportStatus status;
    private String screenshot;
}
