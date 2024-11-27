package net.lodgames.appVersion.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lodgames.appVersion.constants.AppVersionType;
import net.lodgames.appVersion.constants.PublishStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity(name = "app_version")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AppVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "version")
    private String version;

    @Column(name = "version_type")
    private AppVersionType versionType;

    @Column(name = "publish_at")
    private LocalDateTime publishAt;

    @Column(name = "publish_status")
    private PublishStatus publishStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public AppVersion(String version, AppVersionType versionType, LocalDateTime publishAt, PublishStatus publishStatus) {
        this.version = version;
        this.versionType = versionType;
        this.publishAt = publishAt;
        this.publishStatus = publishStatus;
    }
}