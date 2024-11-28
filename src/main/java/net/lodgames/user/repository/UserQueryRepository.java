package net.lodgames.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.user.model.Profile;
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
import static net.lodgames.user.model.QUserBlock.userBlock;

@Repository
@AllArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public UserInfoVo selectUserInfo(UserInfoParam userInfoParam){
        Long userId = userInfoParam.getUserId(); // 요청하는 유저 ID
        Long targetUserId = userInfoParam.getTargetUserId(); // 대상 유저 ID

        return jpaQueryFactory.select(Projections.fields(UserInfoVo.class,
                        users.userId,
                        users.status,
                        profile.nickname,
                        profile.image,
                        profile.basicImageIdx,
                        follow.followId.isNotNull().as("isFollow"), // 요청 유저가 대상 유저를 팔로우하는지
                        userBlock.blockUserId.isNotNull().as("isBlock") // 요청 유저가 대상 유저를 차단했는지
                ))
                .from(users)
                .join(profile).on(profile.userId.eq(users.userId)) // 프로필은 대상 유저와 연결
                .leftJoin(follow).on(follow.userId.eq(userId) // 요청 유저가
                        .and(follow.followId.eq(targetUserId))) // 대상 유저를 팔로우했는지 확인
                .leftJoin(userBlock).on(userBlock.userId.eq(userId) // 요청 유저가
                        .and(userBlock.blockUserId.eq(targetUserId))) // 대상 유저를 차단했는지 확인
                .where(users.userId.eq(targetUserId)) // 대상 유저의 정보를 조회
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

    // 순수 닉네임 검색용
    public Boolean searchUserNickname(String searchUserNickname) {
        // 닉네임에 해당하는 레코드가 하나라도 존재하는지 확인
        Profile result = jpaQueryFactory.selectFrom(profile)
                .join(users).on(users.userId.eq(profile.userId))
                .where(profile.nickname.eq(searchUserNickname))
                .limit(1)  // 하나만 검색되면 종료
                .fetchOne();

        // 검색된 결과가 있으면 true, 없으면 false 반환
        return result != null;
    }
}
