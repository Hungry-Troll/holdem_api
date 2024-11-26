package net.lodgames.friend.repository;

import net.lodgames.friend.param.FriendInfoParam;
import net.lodgames.friend.param.FriendListParam;
import net.lodgames.friend.vo.FriendInfoVo;
import net.lodgames.friend.vo.FriendVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.friend.model.QFriend.friend;
import static net.lodgames.user.model.QProfile.profile;
import static net.lodgames.user.model.QUsers.users;

@Repository
@AllArgsConstructor
public class FriendQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<FriendVo> selectFriendByUserId(FriendListParam friendListParam , Pageable pageable){
        return jpaQueryFactory.select(Projections.bean(FriendVo.class,
                        users.userId,
                        users.status,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx,
                        friend.friendId,
                        friend.createdAt,
                        friend.updatedAt
                ))
                .from(friend)
                .join(users).on(users.userId.eq(friend.friendId))
                .leftJoin(profile).on(profile.userId.eq(users.userId))
                .where(friend.userId.eq(friendListParam.getUserId()))
                .orderBy(friend.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public FriendInfoVo selectFriendByFriendId(FriendInfoParam friendInfoParam){
        return jpaQueryFactory.select(Projections.bean(FriendInfoVo.class,
                        users.userId,
                        users.status,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx,
                        friend.friendId,
                        friend.createdAt,
                        friend.updatedAt
                ))
                .from(friend)
                .join(users).on(users.userId.eq(friend.friendId))
                .leftJoin(profile).on(profile.userId.eq(users.userId))
                .where(friend.userId.eq(friendInfoParam.getUserId())
                        .and(friend.friendId.eq(friendInfoParam.getFriendId())))
                .fetchOne();
    }
}
