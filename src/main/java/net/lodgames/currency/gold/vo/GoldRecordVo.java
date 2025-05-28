package net.lodgames.currency.gold.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.currency.common.constants.ChangeType;

import java.time.LocalDateTime;

@Setter
@Getter
public class GoldRecordVo {
    private Long id;                 // 골드 기록 고유번호
    private Long userId;             // 유저 고유번호
    private ChangeType changeType;   // 변경타입
    private long changeGold;         // 변경 골드
    private long resultGold;         // 결과 골드
    private String changeDesc;       // 변경 설명
    private String idempotentKey;    // 멱등키
    private LocalDateTime createdAt; // 만든날짜

}
