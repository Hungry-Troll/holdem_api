package net.lodgames.inquiry.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.inquiry.constants.InquiryStatus;
import net.lodgames.inquiry.model.Inquiry;
import net.lodgames.inquiry.param.InquiresGetParam;
import net.lodgames.inquiry.param.InquiryAddParam;
import net.lodgames.inquiry.param.InquiryModParam;
import net.lodgames.inquiry.repository.InquiryQueryRepository;
import net.lodgames.inquiry.repository.InquiryRepository;
import net.lodgames.inquiry.util.InquiryMapper;
import net.lodgames.inquiry.vo.InquiresGetVo;
import net.lodgames.inquiry.vo.InquiryGetVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final InquiryQueryRepository inquiryQueryRepository;
    private final InquiryMapper inquiryMapper;

    // 문의 하기
    @Transactional(rollbackFor = {Exception.class})
    public void addInquiry(InquiryAddParam inquiryAddParam) {
        inquiryRepository.save(Inquiry.builder()
                .userId(inquiryAddParam.getUserId())
                .type(inquiryAddParam.getType())
                .reason(inquiryAddParam.getReason())
                .screenshot(inquiryAddParam.getScreenshot())
                .status(InquiryStatus.PROGRESS)
                .build());
    }

    // 문의 리스트
    @Transactional(readOnly = true)
    public List<InquiresGetVo> getInquires(InquiresGetParam inquiresGetParam) {
        return inquiryQueryRepository.getInquires(inquiresGetParam.of());
    }

    // 문의 확인
    @Transactional(readOnly = true)
    public InquiryGetVo getInquiry(Long inquiryId) {
        return inquiryMapper.updateInquiryToVo(findInquiry(inquiryId));
    }

    // 문의 해결
    @Transactional(rollbackFor = {Exception.class})
    public void resolveInquiry(Long inquiryId) {
        Inquiry findInquiry = findInquiry(inquiryId);
        findInquiry.setStatus(InquiryStatus.RESOLVE);
        inquiryRepository.save(findInquiry);
    }

    // 문의 수정
    @Transactional(rollbackFor = {Exception.class})
    public void modInquiry(Long inquiryId, InquiryModParam inquiryModParam) {
        Inquiry findInquiry = findInquiry(inquiryId);

        findInquiry.setType(inquiryModParam.getType());
        findInquiry.setReason(inquiryModParam.getReason());
        findInquiry.setScreenshot(inquiryModParam.getScreenshot());
        inquiryRepository.save(findInquiry);
    }

    // 문의 삭제
    @Transactional(rollbackFor = {Exception.class})
    public void delInquiry(Long inquiryId) {
        inquiryRepository.delete(findInquiry(inquiryId));
    }

    // 문의 찾기
    private Inquiry findInquiry(Long inquiryId) {
        Inquiry findInquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(()-> new RestException(ErrorCode.INQUIRY_NOT_EXIST));
        return findInquiry;
    }
}
