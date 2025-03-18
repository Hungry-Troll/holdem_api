package net.lodgames.relation.report.util;

import net.lodgames.relation.report.model.UserReport;
import net.lodgames.relation.report.vo.GetUserReportVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserReportMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GetUserReportVo updateUserReportToVo(UserReport userReport);
}
