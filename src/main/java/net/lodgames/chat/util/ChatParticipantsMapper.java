package net.lodgames.chat.util;

import net.lodgames.chat.model.ChatParticipant;
import net.lodgames.chat.vo.ParticipantVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatParticipantsMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ParticipantVo updateChatParticipantToParticipantVo(ChatParticipant chatParticipant);
}
