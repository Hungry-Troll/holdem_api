package net.lodgames.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.chat.vo.ParticipantVo;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.chat.model.QChatParticipant.chatParticipant;

@Repository
@RequiredArgsConstructor
public class ChatParticipantQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Long countChatParticipantInRoom(long roomId) {
        return jpaQueryFactory.select(chatParticipant.count())
                .from(chatParticipant)
                .where(chatParticipant.roomId.eq(roomId))
                .fetchFirst();
    }

    public Long countChatParticipantByRoomIdAndUserIdList(Long roomId, List<Long> userIdList) {
        return jpaQueryFactory.select(chatParticipant.count())
                .from(chatParticipant)
                .where(chatParticipant.roomId.eq(roomId)
                        , chatParticipant.userId.in(userIdList))
                .fetchFirst();
    }

    public List<ParticipantVo> selectRoomParticipantByRoomId(long roomId) {
        return jpaQueryFactory
                .select(Projections.fields(ParticipantVo.class,
                        chatParticipant.id,
                        chatParticipant.roomId,
                        chatParticipant.userId,
                        chatParticipant.participantType,
                        chatParticipant.updatedAt,
                        chatParticipant.createdAt
                ))
                .from(chatParticipant)
                .where(chatParticipant.roomId.eq(roomId))
                .fetch();
    }

    public ParticipantVo selectChatParticipantByRoomIdAndUserId(long roomId, long userId) {
        return jpaQueryFactory
                .select(Projections.fields(ParticipantVo.class,
                        chatParticipant.id,
                        chatParticipant.roomId,
                        chatParticipant.userId,
                        chatParticipant.participantType,
                        chatParticipant.updatedAt,
                        chatParticipant.createdAt
                ))
                .from(chatParticipant)
                .where(chatParticipant.roomId.eq(roomId)
                        , chatParticipant.userId.eq(userId))
                .fetchOne();
    }

}
