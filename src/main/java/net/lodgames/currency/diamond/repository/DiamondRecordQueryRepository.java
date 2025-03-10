package net.lodgames.currency.diamond.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.currency.diamond.vo.DiamondRecordVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.currency.diamond.model.QDiamondRecord.diamondRecord;

@Repository
@RequiredArgsConstructor
public class DiamondRecordQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<DiamondRecordVo> findDiamondRecords(Long userId, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(DiamondRecordVo.class,
                        diamondRecord.id,
                        diamondRecord.userId,
                        diamondRecord.changeType,
                        diamondRecord.os,
                        diamondRecord.changeDiamond,
                        diamondRecord.resultFreeDiamond,
                        diamondRecord.resultAndroidDiamond,
                        diamondRecord.resultIosDiamond,
                        diamondRecord.resultPaidDiamond,
                        diamondRecord.changeDesc,
                        diamondRecord.idempotentKey,
                        diamondRecord.createdAt
                )).from(diamondRecord)
                .where(diamondRecord.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(diamondRecord.createdAt.desc())
                .fetch();
    }
}
