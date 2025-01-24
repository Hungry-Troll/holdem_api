package net.lodgames.inquiry.util;

import net.lodgames.inquiry.model.Inquiry;
import net.lodgames.inquiry.vo.InquiryGetVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InquiryMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public InquiryGetVo updateInquiryToVo(Inquiry inquiry);
}
