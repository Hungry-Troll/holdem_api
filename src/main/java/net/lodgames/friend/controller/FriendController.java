package net.lodgames.friend.controller;

import net.lodgames.config.security.UserPrincipal;
import net.lodgames.friend.param.*;
import net.lodgames.friend.service.FriendService;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<FriendVo>> friendList(@RequestBody FriendListParam friendListParam,
                                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendService.getFriendList(friendListParam));
    }

    // 친구 정보 조회
    @GetMapping("/friends/{friendId}")
    public ResponseEntity<?> friendInfo(@PathVariable long friendId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(friendService.getFriendInfo(
                FriendInfoParam.builder()
                        .userId(userPrincipal.getUserId())
                        .friendId(friendId).build())
        );
    }

    // 친구삭제
    @DeleteMapping("/friends")
    public ResponseEntity<?> deleteFriend(@RequestBody FriendDeleteParam friendDeleteParam,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendDeleteParam.setUserId(userPrincipal.getUserId());
        friendService.deleteFriend(friendDeleteParam);
        return ResponseEntity.ok().build();
    }

    // 닉네임 찾기
    @GetMapping("/friends/findUserNickname")
    public ResponseEntity<?> findUserNickname(@RequestBody FindUserNicknameParam findUserNicknameParam,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        findUserNicknameParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendService.findUserNickname(findUserNicknameParam));
    }
}