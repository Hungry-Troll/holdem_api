package net.lodgames.user.model;

import net.lodgames.user.constants.LoginType;
import net.lodgames.user.constants.UserStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity(name = "users")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime initAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime loginAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime logoutAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Transient
    private LoginAddInfo loginAddInfo;

    @Builder
    public Users(long userId, String loginId, UserStatus status, String password, String email, String mobile, LoginType loginType, LocalDateTime initAt) {
        this.userId = userId;
        this.loginId = loginId;
        this.status = status;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.loginType = loginType;
        this.initAt = initAt;
    }
}
