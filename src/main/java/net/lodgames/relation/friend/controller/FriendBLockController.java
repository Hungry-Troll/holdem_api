package net.lodgames.relation.friend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.relation.friend.param.FriendBlockParam;
import net.lodgames.relation.friend.param.FriendListParam;
import net.lodgames.relation.friend.service.FriendBlockService;
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
    public ResponseEntity<?> friendBlockList(@RequestBody FriendListParam friendListParam,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendBlockService.friendBlockList(friendListParam));
    }

    // 친구차단 추가
    @PostMapping("/friends/{friendId}/blocks")
    public ResponseEntity<?> addFriendBlock(@PathVariable(name = "friendId") Long friendId,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendBlockService.addFriendBlock(FriendBlockParam.builder()
                .userId(userPrincipal.getUserId())
                .friendId(friendId)
                .build());
        return ResponseEntity.ok().build();
    }

    // 친구차단 삭제
    @DeleteMapping("/friends/{friendId}/blocks")
    public ResponseEntity<?> deleteFriendBlock(@PathVariable(name ="friendId") Long friendId,
                                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendBlockService.deleteFriendBlock(FriendBlockParam.builder()
                .userId(userPrincipal.getUserId())
                .friendId(friendId)
                .build());
        return ResponseEntity.ok().build();
    }
}