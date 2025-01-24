package net.lodgames.inquiry.constants;

import lombok.Getter;

@Getter
public enum InquiryStatus {
    PROGRESS(0),
    RESOLVE(1);
    final int status;

    InquiryStatus(int status){
        this.status = status;
    }
}
