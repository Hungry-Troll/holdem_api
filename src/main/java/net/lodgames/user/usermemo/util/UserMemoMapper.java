package net.lodgames.user.usermemo.util;

import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMemoMapper {
}
