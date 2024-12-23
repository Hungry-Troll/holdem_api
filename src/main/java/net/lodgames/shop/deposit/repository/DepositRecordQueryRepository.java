package net.lodgames.shop.deposit.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static net.lodgames.shop.deposit.model.QDepositRecord.depositRecord;

@Repository
@RequiredArgsConstructor
public class DepositRecordQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Integer getTotalPaymentPriceWithinMonth(Long userId, LocalDate now) {
        return jpaQueryFactory.select(depositRecord.paymentPrice.sum())
                .from(depositRecord)
                .where(depositRecord.userId.eq(userId),
                        depositRecord.paymentDate.year().eq(now.getYear()),
                        depositRecord.paymentDate.month().eq(now.getMonthValue()))
                .fetchOne();
    }

}
