package net.lodgames.relation.memo.util;

import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMemoMapper {
}
