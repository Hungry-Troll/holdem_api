package net.lodgames.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.friend.param.FriendBlockParam;
import net.lodgames.user.param.UserBlockDeleteParam;
import net.lodgames.user.param.UserBlockListParam;
import net.lodgames.user.param.UserBlockParam;
import net.lodgames.user.service.UserBlockService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserBlockController {

    final UserBlockService userBlockService;
    // 유저 차단 추가
    @PostMapping("/users/{blockUserId}/block")
    public ResponseEntity<?> blockUser(@PathVariable(name="blockUserId") Long blockUserId,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userBlockService.blockUser(UserBlockParam.builder()
                .userId(userPrincipal.getUserId())
                .blockUserId(blockUserId)
                .build());
        return ResponseEntity.ok().build();
    }
    // 유저 차단 리스트
    @GetMapping("/users/blocks")
    public ResponseEntity<?> blockUserList(@RequestBody UserBlockListParam userBlockListParam,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userBlockListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(userBlockService.blockUserList(userBlockListParam));
    }
    // 유저 차단 삭제
    @DeleteMapping("/users/{blockUserId}/block")
    public ResponseEntity<?> deleteBlockUser(@PathVariable(name="blockUserId") Long blockUserId,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userBlockService.deleteBlockUser(UserBlockDeleteParam.builder()
                .userId(userPrincipal.getUserId())
                .blockUserId(blockUserId)
                .build());
        return ResponseEntity.ok().build();
    }
}
