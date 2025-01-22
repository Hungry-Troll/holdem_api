package net.lodgames.relation.report.constants;

import lombok.Getter;

@Getter
public enum UserReportStatus {
    PROGRESS(0),
    RESOLVE(1);
    final int status;

    UserReportStatus(int status){
        this.status = status;
    }
}
