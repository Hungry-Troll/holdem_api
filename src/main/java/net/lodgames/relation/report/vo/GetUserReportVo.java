package net.lodgames.relation.report.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.relation.report.constants.UserReportStatus;

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
