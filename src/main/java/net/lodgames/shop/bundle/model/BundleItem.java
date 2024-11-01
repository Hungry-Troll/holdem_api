package net.lodgames.shop.bundle.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.shop.item.model.Item;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BundleItem {
    @EmbeddedId
    private final BundleItemId bundleItemId = new BundleItemId();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bundle_id", referencedColumnName = "id")
    @MapsId("bundleId")
    private Bundle bundle;          // 번들

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @MapsId("itemId")
    private Item item;              // 아이템

    private Integer count;          // 갯수

    @CreatedDate
    private LocalDateTime createdAt; // 생성
}
