package net.lodgames.relation.memo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.relation.memo.param.UserMemoGetParam;
import net.lodgames.relation.memo.vo.UserMemoGetVo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static net.lodgames.relation.memo.model.QUserMemo.userMemo;

@Repository
@AllArgsConstructor
public class UserMemoQueryRepository {

    final JPAQueryFactory jpaQueryFactory;

    public UserMemoGetVo getMemo(UserMemoGetParam userMemoGetParam) {
        return Optional.ofNullable(
                jpaQueryFactory.select(Projections.fields(UserMemoGetVo.class,
                                userMemo.memoText,
                                userMemo.tag))
                        .from(userMemo)
                        .where(userMemo.userId.eq(userMemoGetParam.getUserId())
                                .and(userMemo.targetUserId.eq(userMemoGetParam.getTargetUserId())))
                        .fetchOne()
        ).orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_MEMO));
    }
}
