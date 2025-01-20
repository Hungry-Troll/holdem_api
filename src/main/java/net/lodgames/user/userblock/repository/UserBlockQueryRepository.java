package net.lodgames.user.userblock.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.user.userblock.param.UserBlockListParam;
import net.lodgames.user.userblock.param.UserBlockParam;
import net.lodgames.user.userblock.vo.UserBlockListVo;
import net.lodgames.user.userblock.vo.UserBlockVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.user.profile.model.QProfile.profile;
import static net.lodgames.user.userblock.model.QUserBlock.userBlock;
import static net.lodgames.user.user.model.QUsers.users;

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
