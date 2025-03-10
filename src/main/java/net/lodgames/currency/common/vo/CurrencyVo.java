package net.lodgames.currency.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.lodgames.user.constants.Os;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyVo {
    @Setter
    @Getter
    private Long userId;
    @Getter
    private Long coinAmount;    // 코인 합계
    @Getter
    private Long chipAmount;    // 칩 합계
    private Long diamondAmount; // 다이아몬드 합계
    @JsonIgnore
    private Long freeAmount;
    @JsonIgnore
    private Long androidAmount;
    @JsonIgnore
    private Long iosAmount;
    @JsonIgnore
    private Long paidAmount;
    @Setter
    private Os os;

    public Long getDiamondAmount() {
        return switch (this.os) {
            case ANDROID -> this.freeAmount + this.androidAmount;
            case IOS -> this.freeAmount + this.iosAmount;
            case null, default -> this.freeAmount + this.paidAmount;
        };
    }
}
