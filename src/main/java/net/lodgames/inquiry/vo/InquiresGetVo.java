package net.lodgames.inquiry.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.inquiry.constants.InquiryStatus;
import net.lodgames.inquiry.constants.InquiryType;

import java.time.LocalDateTime;

@Getter
@Setter
public class InquiresGetVo {
    private long userId;
    private InquiryType type; // PAYMENT(0),ACCESS(1),PLAY(2),FEEDBACK(3),OTHER(4);
    private InquiryStatus status; // PROGRESS(0),RESOLVE(1);
    private LocalDateTime createdAt;
}
