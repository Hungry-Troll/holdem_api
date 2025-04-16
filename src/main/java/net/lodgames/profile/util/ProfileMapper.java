package net.lodgames.profile.util;

import net.lodgames.profile.model.Profile;
import net.lodgames.profile.param.ProfileModParam;
import net.lodgames.profile.vo.ProfileVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProfileVo updateProfileToVo(Profile profile);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromModParam(ProfileModParam param, @MappingTarget Profile profile);
}
