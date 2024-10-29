package net.lodgames.user.model;


import net.lodgames.user.constants.UserStatus;
import net.lodgames.user.constants.UserType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime loginAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime logoutAt;

    @Transient
    private LoginAddInfo loginAddInfo;


    @Builder
    public Users(long userId, UserStatus status, String password, String email, String mobile, UserType userType) {
        this.userId = userId;
        this.status = status;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.userType = userType;
    }
}
