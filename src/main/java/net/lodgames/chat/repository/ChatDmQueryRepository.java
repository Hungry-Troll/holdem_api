package net.lodgames.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.chat.constant.LeaveStatus;
import net.lodgames.chat.param.DmInfoParam;
import net.lodgames.chat.param.DmListParam;
import net.lodgames.chat.vo.ChatDmStatusVo;
import net.lodgames.chat.vo.DmVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static net.lodgames.chat.model.QChatDm.chatDm;

@Repository
@RequiredArgsConstructor
public class ChatDmQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<DmVo> selectChatDmListByUserId(DmListParam dmListParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(DmVo.class,
                        chatDm.id,
                        chatDm.userId.as("senderId"),
                        chatDm.targetId,
                        chatDm.leaveStatus,
                        chatDm.targetStatus,
                        chatDm.createdAt,
                        chatDm.updatedAt
                ))
                .from(chatDm)
                .where(eqUserId(dmListParam.getUserId()),
                        conditionByLastCheckTime(dmListParam.getLastCheckTime())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(chatDm.createdAt.desc())
                .fetch();

    }

    private BooleanExpression eqUserId(Long userId) {
        return userId == null ? null : chatDm.userId.eq(userId);
    }

    private BooleanExpression eqTargetId(Long targetId) {
        return targetId == null ? null : chatDm.targetId.eq(targetId);
    }

    private BooleanExpression conditionByLastCheckTime(LocalDateTime lastCheckTime) {
        if (lastCheckTime == null) {
            return chatDm.leaveStatus.eq(LeaveStatus.STAY);
        } else {
            return chatDm.updatedAt.gt(lastCheckTime);
        }
    }

    public DmVo selectChatDmByUserId(DmInfoParam dmInfoParam) {
        return jpaQueryFactory
                .select(Projections.fields(DmVo.class,
                        chatDm.id,
                        chatDm.userId,
                        chatDm.targetId,
                        chatDm.leaveStatus,
                        chatDm.targetStatus,
                        chatDm.createdAt,
                        chatDm.updatedAt
                ))
                .from(chatDm)
                .where(eqUserId(dmInfoParam.getUserId()),
                        eqTargetId(dmInfoParam.getTargetId())
                )
                .fetchOne();
    }


    public ChatDmStatusVo selectChatDmStatus(Long senderId, Long targetId) {
        return jpaQueryFactory
                .select(Projections.fields(ChatDmStatusVo.class,
                        chatDm.leaveStatus,
                        chatDm.targetStatus
                ))
                .from(chatDm)
                .where(eqUserId(senderId),
                        eqTargetId(targetId)
                )
                .fetchOne();
    }
}
