package net.lodgames.relation.friend.repository;

import net.lodgames.relation.friend.param.FriendInfoParam;
import net.lodgames.relation.friend.param.FriendListParam;
import net.lodgames.relation.friend.param.FriendSearchParam;
import net.lodgames.relation.friend.vo.FriendInfoVo;
import net.lodgames.relation.friend.vo.FriendSearchVo;
import net.lodgames.relation.friend.vo.FriendVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import static net.lodgames.profile.model.QProfile.profile;
import static net.lodgames.relation.friend.model.QFriend.friend;
import static net.lodgames.user.model.QUsers.users;

@Repository
@AllArgsConstructor
public class FriendQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private static final Logger logger = LoggerFactory.getLogger(FriendQueryRepository.class);

    public List<FriendVo> selectFriendByUserId(FriendListParam friendListParam , Pageable pageable){
        return jpaQueryFactory.select(Projections.bean(FriendVo.class,
                        friend.id,
                        friend.friendId,
                        //users.userId, // friend.friendId 와 users.userId 가 같아서 생략
                        users.status,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx,
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
                        friend.id,
                        friend.friendId,
                        //users.userId,
                        users.status,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx,
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

    public List<FriendSearchVo> selectFriendByNickName(FriendSearchParam friendSearchParam, Pageable pageable){
        return jpaQueryFactory.select(Projections.bean(FriendSearchVo.class,
                        friend.id,
                        friend.friendId,
                        users.status,
                        users.logoutAt,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx
                ))
                .from(friend)
                .join(users).on(users.userId.eq(friend.friendId))
                .join(profile).on(profile.userId.eq(users.userId))
                .where(friend.userId.eq(friendSearchParam.getUserId())
                        .and(profile.nickname.contains(friendSearchParam.getNickname())))
                .orderBy(friend.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
