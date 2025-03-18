package net.lodgames.inquiry.util;

import net.lodgames.inquiry.model.Inquiry;
import net.lodgames.inquiry.param.InquiryModParam;
import net.lodgames.inquiry.vo.InquiryGetVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InquiryMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInquireFromModParam(InquiryModParam inquiryModParam, @MappingTarget Inquiry inquiry);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InquiryGetVo updateInquiryToVo(Inquiry inquiry);
}
