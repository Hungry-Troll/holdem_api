package net.lodgames.user.userreport.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.user.userreport.constants.UserReportStatus;
import net.lodgames.user.userreport.param.GetUserReportsParam;
import net.lodgames.user.userreport.vo.GetUserReportsVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.user.userreport.model.QUserReport.userReport;

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
