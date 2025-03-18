package net.lodgames.relation.report.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.relation.report.constants.UserReportStatus;
import net.lodgames.relation.report.model.UserReport;
import net.lodgames.relation.report.param.UserReportAddParam;
import net.lodgames.relation.report.param.UserReportsGetParam;
import net.lodgames.relation.report.param.UserReportModParam;
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
    public void addReportUser(UserReportAddParam userReportAddParam) {
        userReportRepository.save(UserReport.builder()
                .reporterId(userReportAddParam.getReporterId())
                .targetUserId(userReportAddParam.getTargetUserId())
                .reason(userReportAddParam.getReason())
                .screenshot(userReportAddParam.getScreenshot())
                .status(UserReportStatus.PROGRESS)
                .build());
    }

    // 유저 신고 리스트 (관리자)
    @Transactional(readOnly = true)
    public List<GetUserReportsVo> getReportUsers(UserReportsGetParam userReportsGetParam) {
        return userReportQueryRepository.reportUsers(userReportsGetParam, userReportsGetParam.of());
    }

    // 유저 신고 단일 조회 (관리자)
    @Transactional(readOnly = true)
    public GetUserReportVo getReportUser(Long userReportId) {
        UserReport userReport = retrieveReportUser(userReportId);
        return userReportMapper.updateUserReportToVo(userReport);
    }

    // 유저 신고 처리 완료 (관리자)
    @Transactional(rollbackFor = {Exception.class})
    public void resolveReportUser(Long userReportId) {
        UserReport userReport = retrieveReportUser(userReportId);
        userReport.setStatus(UserReportStatus.RESOLVE);
        userReportRepository.save(userReport);
    }

    // 유저 신고 수정 (관리자)
    @Transactional(rollbackFor = {Exception.class})
    public void modReportUser(Long userReportId, UserReportModParam userReportModParam) {
        UserReport userReport = retrieveReportUser(userReportId);
        //
        userReport.setReporterId(userReportModParam.getReporterId());
        userReport.setTargetUserId(userReportModParam.getTargetUserId());
        userReport.setReason(userReportModParam.getReason());
        userReport.setScreenshot(userReportModParam.getScreenshot());
        userReportRepository.save(userReport);
    }

    // 유저 신고 삭제 (테스트용)
    public void delReportUser(Long userReportId) {
        userReportRepository.delete(retrieveReportUser(userReportId));
    }

    // 유저 신고 찾기
    private UserReport retrieveReportUser(Long targetId) {
        return userReportRepository.findById(targetId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_REPORT_NOT_EXIST));
    }
}
