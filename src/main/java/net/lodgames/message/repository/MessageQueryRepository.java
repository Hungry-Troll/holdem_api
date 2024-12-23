package net.lodgames.message.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.message.model.Message;
import net.lodgames.message.param.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static net.lodgames.message.model.QMessage.message;

@Repository
@AllArgsConstructor
public class MessageQueryRepository {

    private final MessageRepository messageRepository;
    private final JPAQueryFactory jpaQueryFactory;

    // TODO: 추후 프로필하고 쪽지 합쳐야 됨
    
    // 받은 쪽지함 읽기
    public List<Message> findReceivedBoxMessage(MessageReceiveBoxParam param, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(Message.class,
                message.id,
                message.senderId,
                message.receiverId,
                message.content,
                message.createdAt,
                message.readAt
                ))
                .from(message)
                .where(message.receiverId.eq(param.getReceiverId()))
                .where(message.deletedAt.isNull())
                .orderBy(message.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    // 보낸 쪽지함 확인
    public List<Message> findSendBoxMessage(MessageSendBoxParam param, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(Message.class,
                        message.id,
                        message.senderId,
                        message.receiverId,
                        message.content,
                        message.createdAt
                        ))
                        .from(message)
                        .where(message.senderId.eq(param.getSenderId()))
                        .where(message.deletedAt.isNull())
                        .orderBy(message.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetch();
    }
}
