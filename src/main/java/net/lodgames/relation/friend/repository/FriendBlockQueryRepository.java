package net.lodgames.relation.friend.repository;

import net.lodgames.relation.friend.param.FriendListParam;
import net.lodgames.relation.friend.vo.FriendBlockVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.profile.model.QProfile.profile;
import static net.lodgames.relation.friend.model.QFriendBlock.friendBlock;
import static net.lodgames.user.model.QUsers.users;

@Repository
@RequiredArgsConstructor
public class FriendBlockQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<FriendBlockVo> selectFriendBlockByUserId(FriendListParam friendListParam, Pageable pageable){
        return jpaQueryFactory
                .select(Projections.fields(FriendBlockVo.class,
                        friendBlock.id,
                        friendBlock.userId,
                        friendBlock.friendId,
                        friendBlock.createdAt,
                        users.status,
                        users.createdAt,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx,
                        profile.updatedAt
                ))
                .from(friendBlock)
                .join(users).on(friendBlock.friendId.eq(users.userId))
                .leftJoin(profile).on(users.userId.eq(profile.userId))
                .where(friendBlock.userId.eq(friendListParam.getUserId()))
                .orderBy(friendBlock.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
