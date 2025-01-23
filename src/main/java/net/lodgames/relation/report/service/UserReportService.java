package net.lodgames.relation.report.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.profile.vo.ModReportUserParam;
import net.lodgames.relation.report.constants.UserReportStatus;
import net.lodgames.relation.report.model.UserReport;
import net.lodgames.relation.report.param.AddUserReportParam;
import net.lodgames.relation.report.param.GetUserReportsParam;
import net.lodgames.relation.report.repository.UserReportQueryRepository;
import net.lodgames.relation.report.repository.UserReportRepository;
import net.lodgames.relation.report.util.UserReportMapper;
import net.lodgames.relation.report.vo.GetUserReportVo;
import net.lodgames.relation.report.vo.GetUserReportsVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserReportService {

    final UserReportRepository userReportRepository;
    final UserReportQueryRepository userReportQueryRepository;
    final UserReportMapper userReportMapper;

    // 유저 신고
    @Transactional(rollbackFor = {Exception.class})
    public void addReportUser(AddUserReportParam addUserReportParam) {
        userReportRepository.save(UserReport.builder()
                .reporterId(addUserReportParam.getReporterId())
                .targetUserId(addUserReportParam.getTargetUserId())
                .reason(addUserReportParam.getReason())
                .screenshot(addUserReportParam.getScreenshot())
                .status(UserReportStatus.PROGRESS)
                .build());
    }

    // 유저 신고 리스트 (관리자)
    @Transactional(readOnly = true)
    public List<GetUserReportsVo> getReportUsers(GetUserReportsParam getUserReportsParam) {
        return userReportQueryRepository.reportUsers(getUserReportsParam, getUserReportsParam.of());
    }

    // 유저 신고 단일 조회 (관리자)
    @Transactional(readOnly = true)
    public GetUserReportVo getReportUser(Long userReportId) {
        UserReport findReport = findReportUser(userReportId);
        return userReportMapper.updateUserReportToVo(findReport);
    }

    // 유저 신고 처리 완료 (관리자)
    @Transactional(rollbackFor = {Exception.class})
    public void resolveReportUser(Long userReportId) {
        UserReport findReport = findReportUser(userReportId);
        findReport.setStatus(UserReportStatus.RESOLVE);
        userReportRepository.save(findReport);
    }

    // 유저 신고 수정 (관리자)
    @Transactional(rollbackFor = {Exception.class})
    public void modReportUser(Long userReportId, ModReportUserParam modReportUserParam) {
        UserReport findReport = findReportUser(userReportId);
        //
        findReport.setReporterId(modReportUserParam.getReporterId());
        findReport.setTargetUserId(modReportUserParam.getTargetUserId());
        findReport.setReason(modReportUserParam.getReason());
        findReport.setScreenshot(modReportUserParam.getScreenshot());
        userReportRepository.save(findReport);
    }

    // 유저 신고 삭제 (테스트용)
    public void delReportUser(Long userReportId) {
        userReportRepository.delete(findReportUser(userReportId));
    }

    // 유저 신고 찾기
    private UserReport findReportUser(Long targetId) {
        UserReport findReport = userReportRepository.findById(targetId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_REPORT_NOT_EXIST));
        return findReport;
    }
}
