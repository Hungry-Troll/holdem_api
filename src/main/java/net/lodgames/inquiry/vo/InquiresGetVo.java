package net.lodgames.inquiry.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.inquiry.constants.InquiryType;

@Getter
@Setter
public class InquiresGetVo {
    private Long userId;
    private InquiryType type; // PAYMENT(0),ACCESS(1),PLAY(2),FEEDBACK(3),OTHER(4);
    private String reason;
    private String screenshot;
}
