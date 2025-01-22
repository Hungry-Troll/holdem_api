package net.lodgames.relation.friend.controller;

import net.lodgames.config.security.UserPrincipal;
import net.lodgames.relation.friend.param.FriendDeleteParam;
import net.lodgames.relation.friend.param.FriendInfoParam;
import net.lodgames.relation.friend.param.FriendListParam;
import net.lodgames.relation.friend.param.FriendSearchParam;
import net.lodgames.relation.friend.service.FriendService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import net.lodgames.relation.friend.vo.FriendVo;
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
    public ResponseEntity<?> friendInfo(@PathVariable(name = "friendId") long friendId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(friendService.getFriendInfo(
                FriendInfoParam.builder()
                        .userId(userPrincipal.getUserId())
                        .friendId(friendId).build())
        );
    }

    // 친구삭제
    @DeleteMapping("/friends/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable(name = "friendId") long friendId,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendService.deleteFriend(FriendDeleteParam.builder()
                    .userId(userPrincipal.getUserId())
                    .friendId(friendId)
                    .build());
        return ResponseEntity.ok().build();
    }

    // 친구 닉네임 검색
    @GetMapping("/friends/nickname")
    public ResponseEntity<?> searchFriend(@RequestBody FriendSearchParam friendSearchParam,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendSearchParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendService.searchFriend(friendSearchParam));
    }
}