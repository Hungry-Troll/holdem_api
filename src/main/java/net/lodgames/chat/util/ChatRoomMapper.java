package net.lodgames.chat.util;

import net.lodgames.chat.model.ChatRoom;
import net.lodgames.chat.vo.RoomParticipantsVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatRoomMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoomParticipantsVo updateChatRoomToRoomParticipantVo(ChatRoom chatRoom);
}
