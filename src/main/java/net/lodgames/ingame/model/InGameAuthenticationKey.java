package net.lodgames.ingame.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InGameAuthenticationKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String authenticationKey;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String additionalInfo;

    @Column(nullable = false)
    private LocalDateTime expireTime;

    @Builder
    public InGameAuthenticationKey(Long userId, String authenticationKey, String additionalInfo, LocalDateTime expireTime) {
        this.userId = userId;
        this.additionalInfo = additionalInfo;
        this.authenticationKey = authenticationKey;
        this.expireTime = expireTime;
    }
}
