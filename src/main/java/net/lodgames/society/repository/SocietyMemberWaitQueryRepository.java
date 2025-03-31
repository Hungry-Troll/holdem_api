package net.lodgames.society.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.society.param.wait.SocietyMemberWaitListParam;
import net.lodgames.society.param.wait.SocietyOwnWaitListParam;
import net.lodgames.society.vo.SocietyMemberWaitVo;
import net.lodgames.society.vo.SocietyWaitVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.profile.model.QProfile.profile;
import static net.lodgames.society.model.QSociety.society;
import static net.lodgames.society.model.QSocietyMemberWait.societyMemberWait;
import static net.lodgames.user.model.QUsers.users;

@Repository
@AllArgsConstructor
public class SocietyMemberWaitQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SocietyMemberWaitVo> selectSocietyMemberWait(SocietyMemberWaitListParam societyMemberWaitListParam, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(SocietyMemberWaitVo.class,
                        societyMemberWait.id,
                        societyMemberWait.societyId,
                        societyMemberWait.userId,
                        societyMemberWait.waitType,
                        societyMemberWait.createdAt,
                        users.status,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx
                )).from(societyMemberWait)
                .join(users).on(societyMemberWait.userId.eq(users.userId))
                .join(profile).on(societyMemberWait.userId.eq(profile.userId))
                .where(societyMemberWait.societyId.eq(societyMemberWaitListParam.getSocietyId()),
                        societyMemberWait.waitType.eq(societyMemberWaitListParam.getWaitType()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(societyMemberWait.createdAt.desc())
                .fetch();
    }

    public List<SocietyWaitVo> selectSocietyOwnWaitList(SocietyOwnWaitListParam societyOwnWaitListParam, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(SocietyWaitVo.class,

                        society.name,
                        societyMemberWait.societyId,
                        societyMemberWait.createdAt,
                        profile.nickname
                        )
                )
                .from(societyMemberWait)
                .join(society).on(societyMemberWait.societyId.eq(society.id))
                .join(users).on(societyMemberWait.userId.eq(users.userId))
                .join(profile).on(societyMemberWait.userId.eq(profile.userId))
                .where(societyMemberWait.userId.eq(societyOwnWaitListParam.getUserId()),
                        societyMemberWait.waitType.eq(societyOwnWaitListParam.getWaitType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(societyMemberWait.createdAt.desc())
                .fetch();
    }
}
