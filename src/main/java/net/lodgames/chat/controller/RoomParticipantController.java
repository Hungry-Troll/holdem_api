package net.lodgames.chat.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.chat.param.RoomInviteParam;
import net.lodgames.chat.param.RoomJoinParam;
import net.lodgames.chat.param.RoomKickParam;
import net.lodgames.chat.param.RoomLeaveParam;
import net.lodgames.chat.service.ParticipantService;
import net.lodgames.config.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class RoomParticipantController {
    
    private final ParticipantService participantService;

    // 채팅방 초대
    @PostMapping("/rooms/{roomId}/invite")
    public ResponseEntity<?> inviteRoom(@PathVariable(value = "roomId") Long roomId,
                                        @RequestBody RoomInviteParam roomInviteParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        roomInviteParam.setUserId(userPrincipal.getUserId());
        roomInviteParam.setRoomId(roomId);
        return ResponseEntity.ok(participantService.inviteRoom(roomInviteParam));
    }

    // 채팅방 참여
    @PostMapping("/rooms/{roomId}/join")
    public ResponseEntity<?> joinRoom(@PathVariable(value = "roomId") Long roomId,
                                      @RequestBody RoomJoinParam roomJoinParam,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        roomJoinParam.setUserId(userPrincipal.getUserId());
        roomJoinParam.setRoomId(roomId);
        return ResponseEntity.ok(participantService.joinRoom(roomJoinParam));
    }

    // 채팅방 나가기
    @PostMapping("/rooms/{roomId}/leave")
    public ResponseEntity<?> leaveRoom(@PathVariable(value = "roomId") Long roomId,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        RoomLeaveParam roomLeaveParam = RoomLeaveParam.builder()
                .userId(userPrincipal.getUserId())
                .roomId(roomId)
                .build();
        participantService.leaveRoom(roomLeaveParam);
        return ResponseEntity.ok().build();
    }

    // 채팅방 내보내기
    @PostMapping("/rooms/{roomId}/user/kick")
    public ResponseEntity<?> kickUserFromRoom(@PathVariable(value = "roomId") Long roomId,
                                              @RequestBody RoomKickParam roomKickParam,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        roomKickParam.setRoomId(roomId);
        roomKickParam.setUserId(userPrincipal.getUserId());
        participantService.kickUserFromRoom(roomKickParam);
        return ResponseEntity.ok().build();
    }
}