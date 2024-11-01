package net.lodgames.currency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lodgames.currency.constants.ChangeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "diamond_record")
@EntityListeners({AuditingEntityListener.class})
@Builder
public class DiamondRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="change_type")
    private ChangeType changeType;

    @Column(name="change_diamond")
    private long changeDiamond;

    @Column(name="result_diamond")
    private long resultDiamond;

    @Column(name="change_desc")
    private String changeDesc;

    @Column(name="idempotent_key")
    private String idempotentKey;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt; // 만든날짜

}
