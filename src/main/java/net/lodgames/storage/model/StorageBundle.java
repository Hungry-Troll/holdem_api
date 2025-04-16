package net.lodgames.storage.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "storage_bundle")
public class StorageBundle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "storage_id")
    private Long storageId;
    @Column(name = "bundle_id")
    private Long bundleId;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
