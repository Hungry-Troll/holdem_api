package net.lodgames.currency.diamond.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.currency.common.constants.ChangeType;

import java.time.LocalDateTime;
@Setter
@Getter
public class DiamondRecordVo {
    private Long id;                 // 다이아몬드 기록 고유번호
    private Long userId;             // 유저 고유번호
    private ChangeType changeType;   // 변경타입
    private long changeChip;         // 변경 다이아몬드
    private long resultChip;         // 결과 다이아몬드
    private String changeDesc;       // 변경 설명
    private String idempotentKey;    // 멱등키
    private LocalDateTime createdAt; // 만든날짜
}
