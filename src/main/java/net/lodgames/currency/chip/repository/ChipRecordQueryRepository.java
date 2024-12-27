package net.lodgames.currency.chip.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.currency.chip.vo.ChipRecordVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.currency.chip.model.QChipRecord.chipRecord;

@Repository
@RequiredArgsConstructor
public class ChipRecordQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ChipRecordVo> findChipRecords(Long userId, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(ChipRecordVo.class,
                        chipRecord.id,
                        chipRecord.userId,
                        chipRecord.changeType,
                        chipRecord.changeChip,
                        chipRecord.resultChip,
                        chipRecord.changeDesc,
                        chipRecord.idempotentKey,
                        chipRecord.createdAt
                )).from(chipRecord)
                .where(chipRecord.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(chipRecord.createdAt.desc())
                .fetch();
    }
}
