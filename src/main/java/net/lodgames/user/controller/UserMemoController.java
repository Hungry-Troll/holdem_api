package net.lodgames.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.user.param.UserMemoAddParam;
import net.lodgames.user.param.UserMemoGetParam;
import net.lodgames.user.param.UserMemoModParam;
import net.lodgames.user.service.UserMemoService;
import net.lodgames.user.param.UserMemoDelParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserMemoController {

    final UserMemoService userMemoService;
    // 추가
    @PostMapping("/users/{targetUserId}/memo")
    public ResponseEntity<?> addMemo(@PathVariable(name = "targetUserId") long targetUserId,
                                     @RequestBody UserMemoAddParam userMemoAddParam,
                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userMemoService.addMemo(UserMemoAddParam.builder()
                .userId(userPrincipal.getUserId())
                .targetUserId(targetUserId)
                .memoText(userMemoAddParam.getMemoText())
                .build());
        return ResponseEntity.ok().build();
    }
    // 조회 (로컬 테스트용)
    @GetMapping("/users/{targetUserId}/memo")
    public ResponseEntity<?> getMemo(@PathVariable(name = "targetUserId") long targetUserId,
                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok().body(userMemoService.getMemo(UserMemoGetParam.builder()
                .userId(userPrincipal.getUserId())
                .targetUserId(targetUserId)
                .build()));
    }
    // 변경
    @PutMapping("/users/{targetUserId}/memo")
    public ResponseEntity<?> modMemo(@PathVariable(name = "targetUserId") long targetUserId,
                                     @RequestBody UserMemoModParam userMemoModParam,
                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userMemoService.modMemo(UserMemoModParam.builder()
                            .userId(userPrincipal.getUserId())
                            .targetUserId(targetUserId)
                            .memoText(userMemoModParam.getMemoText())
                            .build());
        return ResponseEntity.ok().build();
    }
    // 삭제 (로컬 테스트용)
    @DeleteMapping("/users/{targetUserId}/memo")
    public ResponseEntity<?> delMemo(@PathVariable(name = "targetUserId") long targetUserId,
                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userMemoService.delMemo(UserMemoDelParam.builder()
                            .userId(userPrincipal.getUserId())
                            .targetUserId(targetUserId)
                            .build());
        return ResponseEntity.ok().build();
    }
}
