package net.lodgames.relation.friend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.relation.friend.param.FriendListParam;
import net.lodgames.relation.friend.param.FriendRequestParam;
import net.lodgames.relation.friend.service.FriendRequestService;
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
    public ResponseEntity<?> friendRequestSendList(@RequestBody FriendListParam friendListParam,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendRequestService.friendRequestSendList(friendListParam));
    }


    // 친구요청 받은 리스트
    @GetMapping("/friends/requests/receive")
    public ResponseEntity<?> friendRequestList(@RequestBody FriendListParam friendListParam,
                                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(friendRequestService.friendRequestList(friendListParam));
    }

    // 친구요청 등록
    @PostMapping("/friends/{receiver}/requests")
    public ResponseEntity<?> addFriendRequest(@PathVariable(name = "receiver") Long receiver,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendRequestService.addFriendRequest(FriendRequestParam.builder()
                .receiver(receiver)
                .sender(userPrincipal.getUserId())
                .build());
        return ResponseEntity.ok().build();
    }

    // 친구 수락
    @PostMapping("/friends/{sender}/requests/accept")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable(name = "sender") Long sender,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendRequestService.acceptFriendRequest(FriendRequestParam.builder()
                .sender(sender)
                .receiver(userPrincipal.getUserId())
                .build());
        return ResponseEntity.ok().build();
    }

    // 친구요청 받음 삭제 (친구 거절)
    @DeleteMapping("/friends/{sender}/requests/receive")
    public ResponseEntity<?> deleteFriendRequest(@PathVariable(name = "sender") Long sender,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendRequestService.deleteFriendRequest(FriendRequestParam.builder()
                .sender(sender)
                .receiver(userPrincipal.getUserId())
                .build());
        return ResponseEntity.ok().build();
    }

    // 친구요청 전송 삭제 (친구 요청 취소)
    @DeleteMapping("/friends/{receiver}/requests/send")
    public ResponseEntity<?> deleteFriendRequestSend(@PathVariable(name = "receiver") Long receiver,
                                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        friendRequestService.deleteFriendRequest(FriendRequestParam.builder()
                .receiver(receiver)
                .sender(userPrincipal.getUserId())
                .build());
        return ResponseEntity.ok().build();
    }
}