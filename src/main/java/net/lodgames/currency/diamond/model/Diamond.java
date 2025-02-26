package net.lodgames.currency.diamond.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lodgames.user.constants.Os;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "diamond")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Diamond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "free_amount")
    private Long freeAmount;

    @Column(name = "android_amount")
    private Long androidAmount;

    @Column(name = "ios_amount")
    private Long iosAmount;

    @Column(name = "paid_amount")
    private Long paidAmount;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt; // 만든날짜

    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일

    public void changeFreeAmount(long newFreeAmount) {
        this.freeAmount = newFreeAmount;
    }

    public void changeIosAmount(long newIosAmount) {
        this.iosAmount = newIosAmount;
    }

    public void changeAndroidAmount(long newAndroidAmount) {
        this.androidAmount = newAndroidAmount;
    }

    public void changePaidAmount(long newPaidAmount) {
        this.paidAmount = newPaidAmount;
    }

    public void addPaidAmount(Os os, long addAmount) {
        switch (os) {
            case ANDROID -> this.androidAmount += addAmount;
            case IOS -> this.iosAmount += addAmount;
            case null, default -> this.paidAmount += addAmount;
        }
    }

    public void addFreeAmount(long addAmount) {
        this.freeAmount += addAmount;
    }

    public void deductAmount(Os os, long deductAmount) {
        switch (os) {
            case ANDROID -> deductFromAndroid(deductAmount);
            case IOS -> deductFromIos(deductAmount);
            case null, default -> deductFromOther(deductAmount);
        }
    }

    private void deductFromAndroid(long deductAmount) {
        if (this.androidAmount >= deductAmount) {
            this.androidAmount -= deductAmount;
        } else {
            deductFreeAmount(deductAmount, this.androidAmount);
            this.androidAmount = 0L;
        }
    }

    private void deductFromIos(long deductAmount) {
        if (this.iosAmount >= deductAmount) {
            this.iosAmount -= deductAmount;
        } else {
            deductFreeAmount(deductAmount, this.iosAmount);
            this.iosAmount = 0L;
        }
    }

    private void deductFromOther(long deductAmount) {
        if (this.paidAmount >= deductAmount) {
            this.paidAmount -= deductAmount;
        } else {
            deductFreeAmount(deductAmount, this.paidAmount);
            this.paidAmount = 0L;
        }
    }

    private void deductFreeAmount(long deductAmount, long paidAmount) {
        this.freeAmount -= (deductAmount - paidAmount);
    }

    public boolean isNotAbleToDeductAmount(Os os, long deductAmount) {
        return getTotalAmount(os) < deductAmount;
    }

    public long getTotalAmount(Os os) {
        return switch (os) {
            case ANDROID -> this.freeAmount + this.androidAmount;
            case IOS -> this.freeAmount + this.iosAmount;
            case null, default -> this.freeAmount + this.paidAmount;
        };
    }

}
