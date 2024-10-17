package net.lodgames.friend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.friend.param.*;
import net.lodgames.friend.service.FriendRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;


    // 친구요청 전송 리스트
    @GetMapping("/friends/requests/send")
    public ResponseEntity<?> friendRequestSendList(@RequestBody FriendListParam friendListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendRequestService.friendRequestSendList(friendListParam));
    }


    // 친구요청 받은 리스트
    @GetMapping("/friends/requests/receive")
    public ResponseEntity<?> friendRequestList(@RequestBody FriendListParam friendListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendRequestService.friendRequestList(friendListParam));
    }

    // 친구요청 등록
    @PostMapping("/friends/requests")
    public ResponseEntity<?> addFriendRequest(@RequestBody FriendRequestParam friendRequestParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendRequestParam.setSender(userPrincipal.getUserId());
        friendRequestService.addFriendRequest(friendRequestParam);
        return ResponseEntity.ok().build();
    }

    // 친구 수락
    @PostMapping("/friends/requests/accept")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody FriendRequestParam friendRequestParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendRequestParam.setReceiver(userPrincipal.getUserId());
        friendRequestService.acceptFriendRequest(friendRequestParam);
        return ResponseEntity.ok().build();
    }

    // 친구요청 받음 삭제 (친구 거절)
    @DeleteMapping("/friends/requests/receive")
    public ResponseEntity<?> deleteFriendRequest(@RequestBody FriendRequestParam friendRequestParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendRequestParam.setReceiver(userPrincipal.getUserId());
        friendRequestService.deleteFriendRequest(friendRequestParam);
        return ResponseEntity.ok().build();
    }

    // 친구요청 전송 삭제 (친구 요청 취소)
    @DeleteMapping("/friends/requests/send")
    public ResponseEntity<?> deleteFriendRequestSend(@RequestBody FriendRequestParam friendRequestParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendRequestParam.setSender(userPrincipal.getUserId());
        friendRequestService.deleteFriendRequest(friendRequestParam);
        return ResponseEntity.ok().build();
    }

}