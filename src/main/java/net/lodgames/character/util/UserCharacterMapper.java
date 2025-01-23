package net.lodgames.character.util;

import net.lodgames.character.model.UserCharacter;
import net.lodgames.character.param.UserCharacterAddParam;
import net.lodgames.character.vo.UserCharacterGetVo;
import net.lodgames.character.vo.UserCharacterModVo;
import net.lodgames.character.vo.UserCharactersGetVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserCharacterMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<UserCharactersGetVo> userCharactersToGetVo(List<UserCharacter> userCharacterList);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserCharacterGetVo userCharacterToGetVo(UserCharacter userCharacter);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserCharacterModVo userCharacterToModVo(UserCharacter userCharacter);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserCharacter updateAddParamToUserCharacter(UserCharacterAddParam param);
}
