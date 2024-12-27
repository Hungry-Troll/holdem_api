package net.lodgames.currency.coin.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.currency.common.constants.ChangeType;

import java.time.LocalDateTime;
@Setter
@Getter
public class CoinRecordVo {
    private Long id;                 // 코인 기록 고유번호
    private Long userId;             // 유저 고유번호
    private ChangeType changeType;   // 변경타입
    private long changeChip;         // 변경 코인
    private long resultChip;         // 결과 코인
    private String changeDesc;       // 변경 설명
    private String idempotentKey;    // 멱등키
    private LocalDateTime createdAt; // 만든날짜

}
