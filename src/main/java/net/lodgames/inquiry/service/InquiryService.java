package net.lodgames.inquiry.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.inquiry.constants.InquiryStatus;
import net.lodgames.inquiry.model.Inquiry;
import net.lodgames.inquiry.param.InquiresGetByUserIdParam;
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

    // 문의 확인 (유저)
    @Transactional(readOnly = true)
    public List<InquiresGetVo> getInquiresByUserId(Long userId, InquiresGetByUserIdParam inquiresGetByUserIdParam) {
        return inquiryQueryRepository.getInquiresByUserId(userId, inquiresGetByUserIdParam.of());
    }

    // 문의 확인 (관리자)
    @Transactional(readOnly = true)
    public InquiryGetVo getInquiry(Long inquiryId) {
        return inquiryMapper.updateInquiryToVo(retrieve(inquiryId));
    }

    // 문의 리스트 (관리자)
    @Transactional(readOnly = true)
    public List<InquiresGetVo> getInquires(InquiresGetParam inquiresGetParam) {
        return inquiryQueryRepository.getInquires(inquiresGetParam.of());
    }

    // 문의 해결
    @Transactional(rollbackFor = {Exception.class})
    public void resolveInquiry(Long inquiryId) {
        Inquiry inquiry = retrieve(inquiryId);
        inquiry.setStatus(InquiryStatus.RESOLVE);
        inquiryRepository.save(inquiry);
    }

    // 문의 수정
    @Transactional(rollbackFor = {Exception.class})
    public void modInquiry(Long inquiryId, InquiryModParam inquiryModParam) {
        Inquiry inquiry = retrieve(inquiryId);
        inquiryMapper.updateInquireFromModParam(inquiryModParam, inquiry);
        inquiryRepository.save(inquiry);
    }

    // 문의 삭제
    @Transactional(rollbackFor = {Exception.class})
    public void delInquiry(Long inquiryId) {
        inquiryRepository.delete(retrieve(inquiryId));
    }

    // 문의 찾기
    private Inquiry retrieve(Long inquiryId) {
        return inquiryRepository.findById(inquiryId)
                .orElseThrow(()-> new RestException(ErrorCode.INQUIRY_NOT_EXIST));
    }
}
