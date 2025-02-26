package net.lodgames.chat.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.chat.param.ChatMsgParam;
import net.lodgames.chat.param.SendMsgParam;
import net.lodgames.chat.service.RoomMsgService;
import net.lodgames.config.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/msg")
public class RoomMsgController {

    private final RoomMsgService roomMsgService;

    // Group message send to Room (dest)
    @PostMapping("/group/send")
    public ResponseEntity<?> sendGroupMessage(@RequestBody SendMsgParam sendMsgParam,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        sendMsgParam.setSenderId(userPrincipal.getUserId());
        return ResponseEntity.ok(roomMsgService.sendGroupMsg(sendMsgParam));
    }

    // Get group messages from the room (dest)
    @GetMapping(value = "/group/msgs")
    public ResponseEntity<?> getGroupChatMessage(@RequestBody ChatMsgParam chatMsgParam,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        chatMsgParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(roomMsgService.getGroupMessage(chatMsgParam));
    }
}
