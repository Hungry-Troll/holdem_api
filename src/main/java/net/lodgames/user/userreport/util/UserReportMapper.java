package net.lodgames.user.userreport.util;

import net.lodgames.user.userreport.model.UserReport;
import net.lodgames.user.userreport.vo.GetUserReportVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserReportMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public GetUserReportVo updateUserReportToVo(UserReport userReport);
}
