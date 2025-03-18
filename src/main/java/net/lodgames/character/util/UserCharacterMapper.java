package net.lodgames.character.util;

import net.lodgames.character.model.UserCharacter;
import net.lodgames.character.param.UserCharacterModParam;
import net.lodgames.character.vo.UserCharacterVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserCharacterMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserCharacterVo updateUserCharacterToVo(UserCharacter userCharacter);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserCharacterFromModParam(UserCharacterModParam param, @MappingTarget UserCharacter userCharacter);
}
