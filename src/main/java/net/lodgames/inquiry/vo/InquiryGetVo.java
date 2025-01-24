package net.lodgames.inquiry.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.inquiry.constants.InquiryStatus;
import net.lodgames.inquiry.constants.InquiryType;

import java.time.LocalDateTime;

@Getter
@Setter
public class InquiryGetVo {
    private Long userId;
    private InquiryType type;     // Payment(0),Access(1),Play(2),Feedback(3),Other(4)
    private String reason;
    private String screenshot;
    private InquiryStatus status; // PROGRESS(0),RESOLVE(1);
    private LocalDateTime createdAt;
}
