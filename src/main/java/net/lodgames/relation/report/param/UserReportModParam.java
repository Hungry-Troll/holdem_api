package net.lodgames.relation.report.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReportModParam {
    @JsonIgnore
    private Long reporterId; 
    private Long targetUserId;
    private String reason;
    private String screenshot;
}
