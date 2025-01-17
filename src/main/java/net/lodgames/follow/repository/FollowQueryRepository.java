package net.lodgames.follow.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.follow.param.FollowListParam;
import net.lodgames.follow.vo.FollowVo;
import net.lodgames.follow.vo.FolloweeVo;
import net.lodgames.follow.vo.FollowerVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import static net.lodgames.follow.model.QFollow.follow;
import static net.lodgames.user.profile.model.QProfile.profile;
import static net.lodgames.user.user.model.QUsers.users;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 팔로워 : 리스트 나를 팔로우 하고 있는 사람들
    public List<FollowerVo> selectFollowerByUserId(FollowListParam followListParam, Pageable pageable) {
        return jpaQueryFactory.select(Projections.bean(FollowerVo.class,
                        follow.id,
                        follow.userId.as("followerId"),
                        follow.followId.as("ownId"),
                        follow.createdAt,
                        users.userId,
                        users.status,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx
                ))
                .from(follow)
                .join(users).on(follow.followId.eq(users.userId))
                .leftJoin(profile).on(users.userId.eq(profile.userId))
                .where(follow.userId.eq(followListParam.getUserId()))
                .orderBy(follow.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
    // 팔로잉 : 내가 팔로우 하고 있는 사람들 user_id => ownId, follow_id => followeeId
    public List<FolloweeVo> selectFollowerByFollowId(FollowListParam followListParam,Pageable pageable) {
        return jpaQueryFactory.select(Projections.bean(FolloweeVo.class,
                        follow.id,
                        follow.userId.as("ownId"),
                        follow.followId.as("followeeId"),
                        follow.createdAt,
                        users.userId,
                        users.status,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx
                ))
                .from(follow)
                .join(users).on(follow.userId.eq(users.userId))
                .leftJoin(profile).on(users.userId.eq(profile.userId))
                .where(follow.followId.eq(followListParam.getUserId()))
                .orderBy(follow.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

}
