package net.lodgames.shop.collection.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lodgames.shop.collection.constants.CollectPeriodType;
import net.lodgames.shop.item.vo.ItemVo;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CollectionVo {
    private Long id;                      // 컬렉션 고유번호
    private Long userId;                  // 유저 고유번호
    private Long purchaseId;              // 아이템 고유번호
    private CollectPeriodType periodType; // 소유 기간 타입
    private LocalDateTime expireDatetime; // 만료 기한
    private LocalDateTime createdAt;      // 생성시각
    private LocalDateTime updatedAt;      // 변경시각
    private ItemVo item;                  // 아이템 고유번호
    @Override
    public String toString() {
        return "CollectionVo{" +
                "id=" + id +
                ", item=" + item +
                ", userId=" + userId +
                ", purchaseId=" + purchaseId +
                ", periodType=" + periodType +
                ", expireDatetime=" + expireDatetime +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
