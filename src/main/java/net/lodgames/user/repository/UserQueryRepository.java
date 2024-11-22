package net.lodgames.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.user.param.UserInfoParam;
import net.lodgames.user.vo.FindUserNicknameVo;
import net.lodgames.user.param.SearchUserNicknameParam;
import net.lodgames.user.vo.UserInfoVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.follow.model.QFollow.follow;
import static net.lodgames.user.model.QProfile.profile;
import static net.lodgames.user.model.QUsers.users;


@Repository
@AllArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public UserInfoVo selectUserInfo(UserInfoParam userInfoParam){
        return jpaQueryFactory.select(Projections.bean(UserInfoVo.class,
                        users.userId,
                        users.status,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx
                        //follow.followId.isNotNull().as("isFollow")
                ))
                .from(users)
                .join(profile).on(profile.userId.eq(users.userId))
                //.leftJoin(follow).on(follow.userId.eq(users.userId))
                .where(users.userId.eq(userInfoParam.getTargetUserId()))
                .fetchOne();
    }


    public List<FindUserNicknameVo> searchUserNickname(SearchUserNicknameParam searchUserNicknameParam, Pageable pageable){
        return jpaQueryFactory.select(Projections.fields(FindUserNicknameVo.class,
                        users.userId,
                        users.status,
                        users.logoutAt,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx
                ))
                .from(users)
                .join(profile).on(users.userId.eq(profile.userId))
                .where(users.userId.ne(searchUserNicknameParam.getUserId())
                        .and(profile.nickname.containsIgnoreCase(searchUserNicknameParam.getNickname()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
