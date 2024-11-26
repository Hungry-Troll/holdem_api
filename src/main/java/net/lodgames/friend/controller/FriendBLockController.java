package net.lodgames.friend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.friend.param.*;
import net.lodgames.friend.service.FriendBlockService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class FriendBLockController {

    private final FriendBlockService friendBlockService;

    // 친구차단 리스트
    @GetMapping("/friends/blocks")
    public ResponseEntity<?> friendBlockList(@RequestBody FriendListParam friendListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendBlockService.friendBlockList(friendListParam));
    }

    // 친구차단 추가
    @PostMapping("/friends/blocks")
    public ResponseEntity<?> addFriendBlock(@RequestBody FriendBlockParam friendBlockParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendBlockParam.setUserId(userPrincipal.getUserId());
        friendBlockService.addFriendBlock(friendBlockParam);
        return ResponseEntity.ok().build();
    }

    // 친구차단 삭제
    @DeleteMapping("/friends/blocks")
    public ResponseEntity<?> deleteFriendBlock(@RequestBody FriendBlockParam friendBlockParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendBlockParam.setUserId(userPrincipal.getUserId());
        friendBlockService.deleteFriendBlock(friendBlockParam);
        return ResponseEntity.ok().build();
    }
}