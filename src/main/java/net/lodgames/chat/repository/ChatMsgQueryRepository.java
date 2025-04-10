package net.lodgames.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.chat.param.ChatDmMsgParam;
import net.lodgames.chat.param.ChatMsgParam;
import net.lodgames.chat.vo.MsgVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static net.lodgames.chat.model.QChatMsg.chatMsg;


@Repository
@RequiredArgsConstructor
public class ChatMsgQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<MsgVo> selectChatMsgList(ChatDmMsgParam chatDmMsgParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(MsgVo.class,
                        chatMsg.id,
                        chatMsg.senderId,
                        chatMsg.dest,
                        chatMsg.destType,
                        chatMsg.msgType,
                        chatMsg.msgBody,
                        chatMsg.sendTime
                ))
                .from(chatMsg)
                .where(eqDest(chatDmMsgParam.getDest()),
                        conditionByLastCheckTime(chatDmMsgParam.getLastCheckTime())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(chatMsg.sendTime.desc())
                .fetch();

    }

    private BooleanExpression conditionByLastCheckTime(LocalDateTime lastCheckTime) {
        return lastCheckTime == null ? null : chatMsg.sendTime.gt(lastCheckTime);
    }

    private BooleanExpression eqDest(String dest) {
        return dest == null ? null : chatMsg.dest.eq(dest);
    }

    public List<MsgVo> selectGroupMsgList(ChatMsgParam chatMsgParam, PageRequest pageable) {
        return jpaQueryFactory
                .select(Projections.fields(MsgVo.class,
                        chatMsg.id,
                        chatMsg.senderId,
                        chatMsg.dest,
                        chatMsg.destType,
                        chatMsg.msgType,
                        chatMsg.msgBody,
                        chatMsg.sendTime
                ))
                .from(chatMsg)
                .where(eqDest(chatMsgParam.getDest()),
                        conditionByLastCheckTime(chatMsgParam.getLastCheckTime())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(chatMsg.sendTime.desc())
                .fetch();
    }
}
