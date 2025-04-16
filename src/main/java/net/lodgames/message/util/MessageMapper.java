package net.lodgames.message.util;

import net.lodgames.message.model.Message;
import net.lodgames.message.vo.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MessageReadVo updateMessageToReadVo(Message messages);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MessageModVo updateMessageToModVo(Message message);
}
