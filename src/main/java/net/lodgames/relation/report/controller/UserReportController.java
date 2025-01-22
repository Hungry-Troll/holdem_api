package net.lodgames.relation.report.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.profile.vo.ModReportUserParam;
import net.lodgames.relation.report.param.AddUserReportParam;
import net.lodgames.relation.report.param.GetUserReportsParam;
import net.lodgames.relation.report.service.UserReportService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import net.lodgames.config.security.UserPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserReportController {

    private final UserReportService userReportService;
    // 유저 신고
    @PostMapping("/users/{targetUserId}/report")
    public ResponseEntity<?> addReportUser(@PathVariable(name = "targetUserId") Long targetUserId,
                                           @RequestBody AddUserReportParam addUserReportParam,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        addUserReportParam.setReporterId(userPrincipal.getUserId());
        addUserReportParam.setTargetUserId(targetUserId);
        userReportService.addReportUser(addUserReportParam);
        return ResponseEntity.ok().build();
    }

    // 유저 신고 리스트 (관리자)
    @GetMapping("/users/reports")
    public ResponseEntity<?> getReportUsers(@RequestBody GetUserReportsParam getUserReportsParam,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userReportService.getReportUsers(getUserReportsParam));
    }

    // 유저 신고 단일 조회 (관리자)
    @GetMapping("/users/reports/{Id}")
    public ResponseEntity<?> getReportUser(@PathVariable(name = "Id") Long userReportId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(userReportService.getReportUser(userReportId));
    }

    // 유저 신고 해결 (관리자)
    @PutMapping("/users/reports/{Id}/resolve")
    public ResponseEntity<?> resolveReportUser(@PathVariable(name = "Id") Long userReportId,
                                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userReportService.resolveReportUser(userReportId);
        return ResponseEntity.ok().build();
    }

    // 유저 신고 수정 (관리자)
    @PutMapping("/users/reports/{Id}/mod")
    public ResponseEntity<?> modReportUser(@PathVariable(name = "Id") Long userReportId,
                                           @RequestBody ModReportUserParam modReportUserParam,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        modReportUserParam.setReporterId(userPrincipal.getUserId());
        userReportService.modReportUser(userReportId, modReportUserParam);
        return ResponseEntity.ok().build();
    }

    // 유저 신고 삭제 (테스트용)
    @DeleteMapping("/users/reports/{Id}")
    public ResponseEntity<?> delReportUser(@PathVariable(name = "Id") Long userReportId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userReportService.delReportUser(userReportId);
        return ResponseEntity.ok().build();
    }
}
