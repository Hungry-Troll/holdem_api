package net.lodgames.currency.gold.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lodgames.currency.common.constants.ChangeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gold_record")
@EntityListeners({AuditingEntityListener.class})
@Builder
public class GoldRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="change_type")
    private ChangeType changeType;

    @Column(name="change_gold")
    private long changeGold;

    @Column(name="result_gold")
    private long resultGold;

    @Column(name="change_desc")
    private String changeDesc;

    @Column(name="idempotent_key")
    private String idempotentKey;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt; // 만든날짜

}
