package net.lodgames.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.chat.constant.RoomSearchType;
import net.lodgames.chat.param.RoomListParam;
import net.lodgames.chat.vo.RoomVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.chat.model.QChatParticipant.chatParticipant;
import static net.lodgames.chat.model.QChatRoom.chatRoom;
import static org.flywaydb.core.internal.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ChatRoomQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<RoomVo> selectRooms(RoomListParam roomListParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(RoomVo.class,
                        chatRoom.id,
                        chatRoom.name,
                        chatRoom.roomType,
                        chatRoom.capacity,
                        chatRoom.currentUserNum,
                        chatRoom.createdAt,
                        chatRoom.updatedAt,
                        chatRoom.deletedAt
                ))
                .from(chatRoom)
                .orderBy(chatRoom.createdAt.desc())
                .where(
                        keywordMatch(roomListParam.getSearchType(),roomListParam.getSearchValue())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    private BooleanExpression keywordMatch(RoomSearchType roomSearchType, String value) {
        if (roomSearchType == null || !hasText(value)) {
            return null;
        }
        String searchValue = value.trim();
        return switch (roomSearchType) {
            case NAME -> chatRoom.name.containsIgnoreCase(searchValue);
        };
    }


    public List<RoomVo> selectRoomsByUserId(RoomListParam roomListParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(RoomVo.class,
                        chatRoom.id,
                        chatRoom.name,
                        chatRoom.idCode,  // 참가자만 조회 가능
                        chatRoom.roomType,
                        chatRoom.capacity,
                        chatRoom.currentUserNum,
                        chatRoom.createdAt,
                        chatRoom.updatedAt,
                        chatRoom.deletedAt
                ))
                .from(chatParticipant)
                .join(chatRoom).on(chatParticipant.roomId.eq(chatRoom.id))
                .where(chatParticipant.userId.eq(roomListParam.getUserId()))
                .orderBy(chatRoom.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


}
