package net.lodgames.inquiry.constants;

import lombok.Getter;

@Getter
public enum InquiryType {
    PAYMENT(0),
    ACCESS(1),
    PLAY(2),
    FEEDBACK(3),
    OTHER(4);

    final int status;
    InquiryType(int status) {this.status = status;}
}
