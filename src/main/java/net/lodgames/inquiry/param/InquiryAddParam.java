package net.lodgames.inquiry.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.inquiry.constants.InquiryType;

@Getter
@Setter
public class InquiryAddParam {
    @JsonIgnore
    private Long userId;
    private InquiryType type; // 문의 종류         PAYMENT(0),ACCESS(1),PLAY(2),FEEDBACK(3),OTHER(4)
    private String reason;    // 문의 내용
    private String screenshot;// 스크린샷 저장 경로
}
