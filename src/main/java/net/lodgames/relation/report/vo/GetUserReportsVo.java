package net.lodgames.relation.report.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserReportsVo {
    private long id;
    private long reporterId;
    private long targetUserId;
    private String reason;
}
