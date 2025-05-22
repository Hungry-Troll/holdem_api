package net.lodgames.message.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.message.param.*;
import net.lodgames.message.vo.MessageBoxVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.message.model.QMessage.message;
import static net.lodgames.profile.model.QProfile.profile;

@Repository
@AllArgsConstructor
public class MessageQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO: 추후 프로필하고 쪽지 합쳐야 됨
    // 받은 쪽지함 읽기
    public List<MessageBoxVo> getMessageBoxesByReceiver(MessageReceiveBoxParam param, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(MessageBoxVo.class,
                message.id,
                message.senderId,
                message.receiverId,
                message.content,
                message.createdAt,
                message.readAt,
                profile.nickname,
                profile.image,
                profile.basicImageIdx
                ))
                .from(message)
                .where(message.receiverId.eq(param.getReceiverId()),
                       message.deletedAt.isNull())
                .join(profile).on(message.senderId.eq(profile.userId)) // 프로필과 조인
                .orderBy(message.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    // 보낸 쪽지함 확인
    public List<MessageBoxVo> getMessageBoxesBySender(MessageSendBoxParam param, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(MessageBoxVo.class,
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
