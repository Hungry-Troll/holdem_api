package net.lodgames.relation.block.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.relation.block.param.UserBlockListParam;
import net.lodgames.relation.block.param.UserBlockParam;
import net.lodgames.relation.block.vo.UserBlockListVo;
import net.lodgames.relation.block.vo.UserBlockVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.profile.model.QProfile.profile;
import static net.lodgames.relation.block.model.QUserBlock.userBlock;
import static net.lodgames.user.model.QUsers.users;


@Repository
@RequiredArgsConstructor
public class UserBlockQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public UserBlockVo blockUser(UserBlockParam userBlockParam) {
        return jpaQueryFactory.select(Projections.fields(UserBlockVo.class,
                        users.userId,
                        userBlock.blockUserId
                ))
                .from(users)
                .join(userBlock).on(users.userId.eq(userBlock.userId))
                .where(userBlock.userId.eq(userBlockParam.getUserId())
                        .and(userBlock.blockUserId.eq(userBlockParam.getBlockUserId()))
                )
                .fetchOne();
    }

    public List<UserBlockListVo> selectBlockUserList(UserBlockListParam userBlockListParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(UserBlockListVo.class,
                    users.userId,
                    users.logoutAt,
                    userBlock.blockUserId,
                    profile.nickname,
                    profile.image,
                    profile.basicImageIdx
                ))
                .from(users)
                .join(userBlock).on(users.userId.eq(userBlockListParam.getUserId()))
                .leftJoin(profile).on(profile.userId.eq(userBlock.blockUserId))
                .where(userBlock.userId.eq(userBlockListParam.getUserId()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
