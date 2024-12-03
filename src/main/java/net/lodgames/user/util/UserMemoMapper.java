package net.lodgames.user.util;

import net.lodgames.user.model.UserMemo;
import net.lodgames.user.param.UserMemoAddParam;
import net.lodgames.user.vo.UserMemoAddVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMemoMapper {
}
