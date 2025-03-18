package net.lodgames.storage.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "storage_item")
@EntityListeners(AuditingEntityListener.class)
public class StorageItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "storage_id")
    private Long storageId;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name ="item_num")
    private Integer itemNum;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
