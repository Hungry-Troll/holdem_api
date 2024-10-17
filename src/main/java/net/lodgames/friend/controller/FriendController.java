package net.lodgames.friend.controller;

import net.lodgames.config.security.UserPrincipal;
import net.lodgames.friend.param.*;
import net.lodgames.friend.service.FriendService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import net.lodgames.friend.vo.FriendVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class FriendController {

    private final FriendService friendService;

    // 친구 리스트
    @GetMapping("/friends")
    public ResponseEntity<List<FriendVo>> friendList(@RequestBody FriendListParam friendListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendService.getFriendList(friendListParam));
    }

    // 친구 정보 조회
    @GetMapping("/friends/{friendId}")
    public ResponseEntity<?> friendInfo(@PathVariable long friendId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(friendService.getFriendInfo(
                FriendInfoParam.builder()
                        .userId(userPrincipal.getUserId())
                        .friendId(friendId).build())
        );
    }

    // 친구삭제
    @DeleteMapping("/friends")
    public ResponseEntity<?> deleteFriend(@RequestBody FriendDeleteParam friendDeleteParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendDeleteParam.setUserId(userPrincipal.getUserId());
        friendService.deleteFriend(friendDeleteParam);
        return ResponseEntity.ok().build();
    }

    // 사람 찾기
    @PostMapping("/friends/search")
    public ResponseEntity<?> findFriend(@RequestBody FindFriendParam findFriendParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        findFriendParam.setUserId(userId);
        return ResponseEntity.ok(friendService.findFriend(findFriendParam));
    }
}