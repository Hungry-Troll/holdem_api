package net.lodgames.payment.google.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.payment.google.vo.GooglePaymentGetNicknameVo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static net.lodgames.payment.google.model.QGooglePayment.googlePayment;
import static net.lodgames.profile.model.QProfile.profile;

@Repository
@RequiredArgsConstructor
public class GooglePaymentQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    // 닉네임으로 결제 정보 조회
    public Optional<GooglePaymentGetNicknameVo> getPaymentByNickname(String nickname) {
        GooglePaymentGetNicknameVo result = jpaQueryFactory.select(Projections.fields(GooglePaymentGetNicknameVo.class,
                profile.nickname,
                googlePayment.userId,
                googlePayment.productId,
                googlePayment.orderId,
                googlePayment.googlePaymentLog
                ))
                .from(profile)
                .join(googlePayment).on(profile.userId.eq(googlePayment.userId))
                .where(profile.nickname.eq(nickname))
                .fetchOne();

        return result == null ? Optional.empty() : Optional.of(result);
    }
}
