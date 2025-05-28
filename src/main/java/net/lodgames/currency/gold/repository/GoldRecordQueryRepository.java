package net.lodgames.currency.gold.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.currency.gold.vo.GoldRecordVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.currency.gold.model.QGoldRecord.goldRecord;

@Repository
@RequiredArgsConstructor
public class GoldRecordQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<GoldRecordVo> findGoldRecords(Long userId, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(GoldRecordVo.class,
                        goldRecord.id,
                        goldRecord.userId,
                        goldRecord.changeType,
                        goldRecord.changeGold,
                        goldRecord.resultGold,
                        goldRecord.changeDesc,
                        goldRecord.idempotentKey,
                        goldRecord.createdAt
                )).from(goldRecord)
                .where(goldRecord.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(goldRecord.createdAt.desc())
                .fetch();
    }
}
