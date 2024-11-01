package net.lodgames.shop.bundle.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lodgames.currency.constants.CurrencyType;
import net.lodgames.shop.bundle.constants.BundleStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class BundleVo {
    private Long id;                     // 번들 고유번호
    private String name;                 // 이름
    private String sku;                  // sku
    private String description;          // 설명
    private BundleStatus status;         // 상태
    private String thumbnail;            // 썸네일
    private String image;                // 이미지
    private String info;                 // 정보
    private Integer countPerPerson;      // 한명당 구매 가능 개수
    private LocalDateTime saleStartDate; // 판매 시작일
    private LocalDateTime saleEndDate;   // 판매 종료일
    private CurrencyType currencyType;   // 재화타입 (구매)
    private int amount;                  // 판매가격
    private int originAmount;            // 원판매가격
    private Integer stockQuantity;       // 재고갯수
    private LocalDateTime createdAt;     // 만든날짜
    private LocalDateTime updatedAt;     // 변경일

    @Override
    public String toString() {
        return "BundleVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", thumbnail='" + thumbnail + '\'' +
                ", image='" + image + '\'' +
                ", info='" + info + '\'' +
                ", countPerPerson=" + countPerPerson +
                ", saleStartDate=" + saleStartDate +
                ", saleEndDate=" + saleEndDate +
                ", currencyType=" + currencyType +
                ", amount=" + amount +
                ", originAmount=" + originAmount +
                ", stockQuantity=" + stockQuantity +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
