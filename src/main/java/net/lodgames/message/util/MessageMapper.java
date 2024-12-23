package net.lodgames.message.util;


import net.lodgames.message.model.Message;
import net.lodgames.message.param.MessageAddParam;
import net.lodgames.message.vo.*;
import net.lodgames.user.model.Profile;
import net.lodgames.user.param.ProfileModParam;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MessageReadVo updateReadMessageToVo(Message messages);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<MessageReceiveBoxVo> updateMessageListToVoList(List<Message> messages);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MessageUpdateVo updateMessageToVo(Message message);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<MessageSendBoxVo> updateCheckSendMessageToVo(List<Message> message);
}
