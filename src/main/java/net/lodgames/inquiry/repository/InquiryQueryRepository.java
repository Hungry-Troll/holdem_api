package net.lodgames.inquiry.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.inquiry.constants.InquiryStatus;
import net.lodgames.inquiry.vo.InquiresGetVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.inquiry.model.QInquiry.inquiry;

@Repository
@RequiredArgsConstructor
public class InquiryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 전체 조회
    public List<InquiresGetVo> getInquires(Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(InquiresGetVo.class,
                    inquiry.userId,
                    inquiry.type,
                    inquiry.reason,
                    inquiry.screenshot
                ))
                .from(inquiry)
                .where(inquiry.status.eq(InquiryStatus.PROGRESS)) // 잰행중 인것만 조회
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(inquiry.id.asc())
                .fetch();
    }
}
