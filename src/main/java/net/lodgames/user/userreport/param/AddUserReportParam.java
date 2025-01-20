package net.lodgames.user.userreport.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserReportParam {
    @JsonIgnore
    private Long reporterId; // 신고자
    @JsonIgnore
    private Long targetUserId; // 신고 대상
    private String reason; // 신고 사유
    private String screenshot; // 스크린샷
}
