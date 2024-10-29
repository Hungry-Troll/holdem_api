package net.lodgames.user.util;

import net.lodgames.user.model.Profile;
import net.lodgames.user.param.ProfileModParam;
import net.lodgames.user.vo.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProfileVo updateProfileToVo(Profile profile);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromModParam(ProfileModParam param, @MappingTarget Profile profile);
}
