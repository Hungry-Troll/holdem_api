package net.lodgames.currency.diamond.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lodgames.currency.common.constants.ChangeType;
import net.lodgames.user.constants.Os;
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
    private Long id; // 다이아몬드 기록 고유번호

    @Column(name="user_id")
    private Long userId; // 유저 고유번호

    @Column(name="change_type")
    private ChangeType changeType; // 변경타입

    @Column(name="os")
    private Os os; // os

    @Column(name="change_diamond")
    private long changeDiamond; // 변경 다이아몬드

    @Column(name="result_free_diamond")
    private long resultFreeDiamond; // 결과 무료 다이아몬드

    @Column(name="result_android_diamond")
    private long resultAndroidDiamond; // 결과 안드로이드 다이아몬드

    @Column(name="result_ios_diamond")
    private long resultIosDiamond; // 결과 아이폰 다이아몬드

    @Column(name="result_paid_diamond")
    private long resultPaidDiamond; // 결과 아이폰 다이아몬드

    @Column(name="change_desc")
    private String changeDesc; // 변경 설명

    @Column(name="idempotent_key")
    private String idempotentKey; // 멱등키

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt; // 만든날짜

}
