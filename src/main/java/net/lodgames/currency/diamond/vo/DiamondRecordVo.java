package net.lodgames.currency.diamond.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.currency.common.constants.ChangeType;
import net.lodgames.user.constants.Os;

import java.time.LocalDateTime;

@Setter
@Getter
public class DiamondRecordVo {
    private Long id;                   // 다이아몬드 기록 고유번호
    private Long userId;               // 유저 고유번호
    private ChangeType changeType;     // 변경타입
    private Os os;                     // os
    private long changeDiamond;        // 변경 다이아몬드
    private long resultFreeDiamond;    // 결과 무료 다이아몬드
    private long resultAndroidDiamond; // 결과 안드로이드 다이아몬드
    private long resultIosDiamond;     // 결과 아이폰 다이아몬드
    private long resultPaidDiamond;    // 결과 지불 다이아몬드
    private String changeDesc;         // 변경 설명
    private String idempotentKey;      // 멱등키
    private LocalDateTime createdAt;   // 만든날짜
}
