package net.lodgames.society.repository;


import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.society.constants.MemberType;
import net.lodgames.society.param.SearchSocietyListParam;
import net.lodgames.society.param.SocietyListParam;
import net.lodgames.society.vo.SocietyInfoVo;
import net.lodgames.society.vo.SocietyLeaderVo;
import net.lodgames.society.vo.SocietyVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.count;
import static net.lodgames.society.model.QSociety.society;
import static net.lodgames.society.model.QSocietyMember.societyMember;

@Repository
@AllArgsConstructor
public class SocietyQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SocietyVo> findSocietyByUserIdAndMemberType(SocietyListParam societyListParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(SocietyVo.class,
                        society.id,
                        society.name,
                        society.info,
                        society.tag,
                        society.joinType,
                        society.image,
                        society.backImage,
                        society.createdAt
                )).from(society)
                .join(societyMember).on(society.id.eq(societyMember.societyId))
                .where(societyMember.userId.eq(societyListParam.getUserId())
                        , eqTypeNum(societyListParam.getMemberType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(societyMember.createdAt.desc())
                .fetch();
    }

    private BooleanExpression eqTypeNum(MemberType memberType) {
        return memberType == null ? null : societyMember.memberType.eq(memberType);
    }

    public SocietyInfoVo findSocietyBySocietyId(Long societyId) {
        return jpaQueryFactory
                .select(Projections.fields(SocietyInfoVo.class,
                        society.id,
                        society.name,
                        society.info,
                        society.tag,
                        society.joinType,
                        society.image,
                        society.backImage,
                        society.createdAt,
                        ExpressionUtils.as(
                                JPAExpressions.select(count(societyMember.id))
                                        .from(societyMember)
                                        .where(societyMember.societyId.eq(societyId)),
                                "memberCount")
                )).from(society)
                .where(society.id.eq(societyId))
                .fetchOne();
    }

    public List<SocietyVo> searchSocieties(SearchSocietyListParam searchSocietyListParam, Pageable pageable) {
        String keyword = searchSocietyListParam.getKeyword();
        return jpaQueryFactory
                .select(Projections.fields(SocietyVo.class,
                        society.id,
                        society.name,
                        society.info,
                        society.tag,
                        society.joinType,
                        society.image,
                        society.backImage,
                        society.createdAt
                ))
                .from(society)
                .where(
                        society.name.contains(keyword)
                                .or(society.info.contains(keyword))
                                .or(society.tag.contains(keyword))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(societyMember.createdAt.desc())
                .fetch();
    }

    public SocietyLeaderVo selectSocietyAndSocietyLeader(long societyId) {
        return jpaQueryFactory
                .select(
                        Projections.fields(SocietyLeaderVo.class,
                        society.joinType,
                        society.passcode,
                        societyMember.userId
                ))
                .from(society)
                .join(societyMember).on(society.id.eq(societyMember.societyId))
                .where(society.id.eq(societyId), societyMember.memberType.eq(MemberType.LEADER))
                .orderBy(societyMember.createdAt.desc())
                .fetchOne();
    }
}