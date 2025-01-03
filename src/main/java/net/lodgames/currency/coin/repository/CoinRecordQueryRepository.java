package net.lodgames.currency.coin.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.currency.coin.vo.CoinRecordVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.currency.coin.model.QCoinRecord.coinRecord;

@Repository
@RequiredArgsConstructor
public class CoinRecordQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<CoinRecordVo> findCoinRecords(Long userId, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(CoinRecordVo.class,
                        coinRecord.id,
                        coinRecord.userId,
                        coinRecord.changeType,
                        coinRecord.changeCoin,
                        coinRecord.resultCoin,
                        coinRecord.changeDesc,
                        coinRecord.idempotentKey,
                        coinRecord.createdAt
                )).from(coinRecord)
                .where(coinRecord.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(coinRecord.createdAt.desc())
                .fetch();
    }
}
