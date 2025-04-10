package net.lodgames.chat.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.chat.param.*;
import net.lodgames.chat.service.RoomService;
import net.lodgames.chat.vo.RoomParticipantsVo;
import net.lodgames.config.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class RoomController {

    private final RoomService roomService;

    // 채팅방 리스트 조회
    @GetMapping("/rooms")
    public ResponseEntity<?> roomList(@RequestBody RoomListParam roomListParam,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        roomListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(roomService.getRoomList(roomListParam));
    }

    // 나의 채팅방 리스트 조회
    @GetMapping("/rooms/mine")
    public ResponseEntity<?> myRoomList(@RequestBody RoomListParam roomListParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        roomListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(roomService.getParticipatedRoomList(roomListParam));
    }

    // 채팅방 단일 정보 조회
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<?> roomInfo(@PathVariable(value = "roomId") Long roomId,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        RoomInfoParam roomInfoParam = RoomInfoParam.builder()
                .userId(userPrincipal.getUserId())
                .roomId(roomId)
                .build();
        return ResponseEntity.ok(roomService.getRoomInfo(roomInfoParam));
    }

    // 채팅방 생성 ( 나만 참가 )
    @PostMapping("/rooms")
    public ResponseEntity<?> addRoom(@RequestBody RoomAddParam roomAddParam,
                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        roomAddParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(roomService.addRoom(roomAddParam));
    }

    // 채팅방 생성 ( 참가자와 같이 )
    @PostMapping("/rooms/group")
    public ResponseEntity<?> addGroupRoom(@RequestBody RoomGroupAddParam roomGroupAddParam,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        roomGroupAddParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(roomService.addGroupRoom(roomGroupAddParam));
    }

    // 채팅방 수정
    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<RoomParticipantsVo> modRoom(@PathVariable(value = "roomId") Long roomId,
                                                      @RequestBody RoomModParam roomModParam,
                                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        roomModParam.setUserId(userPrincipal.getUserId());
        roomModParam.setRoomId(roomId);
        return ResponseEntity.ok(roomService.modRoom(roomModParam));
    }

}