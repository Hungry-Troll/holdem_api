package net.lodgames.relation.friend.repository;


import net.lodgames.relation.friend.param.NoneFriendInfoParam;
import net.lodgames.relation.friend.vo.NoneFriendInfoVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static net.lodgames.profile.model.QProfile.profile;
import static net.lodgames.relation.friend.model.QFriendRequest.friendRequest;
import static net.lodgames.user.model.QUsers.users;


@Repository
@RequiredArgsConstructor
public class NoneFriendQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public NoneFriendInfoVo selectNoneFriendByNoneFriendId(NoneFriendInfoParam noneFriendInfoParam) {
        return jpaQueryFactory.select(Projections.bean(NoneFriendInfoVo.class,
                        users.userId,
                        profile.image,
                        profile.basicImageIdx,
                        profile.nickname,
                        friendRequest.receiver.isNotNull().as("isRequest")
                ))
                .from(users)
                .join(profile).on(users.userId.eq(profile.userId))
                .leftJoin(friendRequest).on(friendRequest.receiver.eq(users.userId).and(friendRequest.sender.eq(noneFriendInfoParam.getUserId())))
                .where(users.userId.eq(noneFriendInfoParam.getNoneFriendId()))
                .fetchOne();
    }
}
