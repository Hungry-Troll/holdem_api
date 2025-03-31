package net.lodgames.society.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.society.param.member.SocietyMemberListParam;
import net.lodgames.society.param.member.SocietyMemberValidationParam;
import net.lodgames.society.vo.SocietyMemberVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.profile.model.QProfile.profile;
import static net.lodgames.society.model.QSocietyMember.societyMember;
import static net.lodgames.user.model.QUsers.users;

@Repository
@AllArgsConstructor
public class SocietyMemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SocietyMemberVo> selectSocietyMember(SocietyMemberListParam societyMemberListParam, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(SocietyMemberVo.class,
                        societyMember.id,
                        societyMember.societyId,
                        societyMember.userId,
                        societyMember.memberType,
                        societyMember.createdAt,
                        societyMember.updatedAt,
                        users.status.as("userStatus"),
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx
                )).from(societyMember)
                .join(users).on(societyMember.userId.eq(users.userId))
                .join(profile).on(societyMember.userId.eq(profile.userId))
                .where(societyMember.societyId.eq(societyMemberListParam.getSocietyId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(societyMember.memberType.asc(), societyMember.createdAt.desc())
                .fetch();
    }

    public Long findSocietyBySocietyIdAndUserId(SocietyMemberValidationParam societyMemberValidationParam) {
        return jpaQueryFactory.select(societyMember.count())
                .from(societyMember)
                .where(societyMember.societyId.eq(societyMemberValidationParam.getSocietyId())
                        .and(societyMember.userId.eq(societyMemberValidationParam.getUserId())))
                .fetchFirst();
    }
}
