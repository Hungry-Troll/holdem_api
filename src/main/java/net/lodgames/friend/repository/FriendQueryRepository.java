package net.lodgames.friend.repository;

import net.lodgames.friend.param.FindUserNicknameParam;
import net.lodgames.friend.param.FriendInfoParam;
import net.lodgames.friend.param.FriendListParam;
import net.lodgames.friend.vo.FindUserNicknameVo;
import net.lodgames.friend.vo.FriendInfoVo;
import net.lodgames.friend.vo.FriendVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.follow.model.QFollow.follow;
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

    public List<FindUserNicknameVo> findUserNickname(FindUserNicknameParam findUserNicknameParam, Pageable pageable){
        return jpaQueryFactory.select(Projections.fields(FindUserNicknameVo.class,
                users.userId,
                profile.nickname,
                profile.image,
                profile.basicImageIdx,
                follow.followId.isNotNull().as("isFollow")
                //friend.friendId.isNotNull().as("isFriend"),
                //friendRequest.receiver.isNotNull().as("isRequest")
        ))
                .from(users)
                .join(profile).on(users.userId.eq(profile.userId))
                // .leftJoin(friend).on(friend.userId.eq(findUserNicknameParam.getUserId()).and(friend.friendId.eq(users.userId)))
                // .leftJoin(friendRequest).on(friendRequest.receiver.eq(users.userId).and(friendRequest.sender.eq(findUserNicknameParam.getUserId())))
                .leftJoin(follow).on(follow.userId.eq(findUserNicknameParam.getUserId()))
                .where(users.userId.ne(findUserNicknameParam.getUserId())
                        .and(profile.nickname.containsIgnoreCase(findUserNicknameParam.getNickname()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
