package net.lodgames.relation.report.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.relation.report.constants.UserReportStatus;
import net.lodgames.relation.report.param.GetUserReportsParam;
import net.lodgames.relation.report.vo.GetUserReportsVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.relation.report.model.QUserReport.userReport;

@Repository
@RequiredArgsConstructor
public class UserReportQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 전체 조회 (PROGRESS)
    public List<GetUserReportsVo> reportUsers(GetUserReportsParam getUserReportsParam, Pageable pageable){
        return jpaQueryFactory
                .select(Projections.fields(GetUserReportsVo.class,
                    userReport.id,
                    userReport.reporterId,
                    userReport.targetUserId,
                    userReport.reason
                ))
                .from(userReport)
                .where(userReport.status.eq(UserReportStatus.PROGRESS)) // 진행중인것만 조회
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(userReport.id.asc())
                .fetch();
    }
}
