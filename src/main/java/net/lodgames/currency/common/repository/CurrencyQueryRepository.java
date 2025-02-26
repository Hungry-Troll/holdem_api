package net.lodgames.currency.common.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.currency.common.vo.CurrencyVo;
import org.springframework.stereotype.Repository;

import static net.lodgames.currency.chip.model.QChip.chip;
import static net.lodgames.currency.coin.model.QCoin.coin;
import static net.lodgames.currency.diamond.model.QDiamond.diamond;

@Repository
@RequiredArgsConstructor
public class CurrencyQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CurrencyVo findCurrenciesByUserId(Long userId) {
        return jpaQueryFactory.select(Projections.fields(CurrencyVo.class,
                        coin.amount.as("coinAmount"),
                        chip.amount.as("chipAmount"),
                        diamond.iosAmount,
                        diamond.androidAmount,
                        diamond.paidAmount,
                        diamond.freeAmount
                        //, diamond.amount.as("diamondAmount")
                )).from(coin)
                .leftJoin(chip).on(coin.userId.eq(chip.userId))
                .leftJoin(diamond).on(coin.userId.eq(diamond.userId))
                .where(coin.userId.eq(userId))
                .orderBy(coin.createdAt.desc())
                .fetchOne();
    }
}
