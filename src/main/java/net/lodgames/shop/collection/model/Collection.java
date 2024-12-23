package net.lodgames.shop.collection.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lodgames.shop.collection.constants.CollectPeriodType;
import net.lodgames.shop.collection.constants.CollectionActivation;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners({AuditingEntityListener.class})
@Entity(name = "collection")
@Builder
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                         // 컬렉션 고유번호
    private Long itemId;                     // 아이템 고유번호
    private Long userId;                     // 유저 고유번호
    private Long purchaseId;                 // 아이템 고유번호
    private CollectPeriodType periodType;    // 기간 타입
    private LocalDateTime expireDatetime;    // 만료일
    private CollectionActivation activation; // 보유 활성  
    @CreatedDate
    private LocalDateTime createdAt;         // 만든날짜
    @LastModifiedDate
    private LocalDateTime updatedAt;         // 변경일
}
