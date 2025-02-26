package net.lodgames.chat.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.chat.param.ChatDmMsgParam;
import net.lodgames.chat.param.SendDmMsgParam;
import net.lodgames.chat.service.DmMsgService;
import net.lodgames.config.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/msg")
public class DmMsgController {

    private final DmMsgService dmMsgService;


    // 1:1 (Direct Message) send
    @PostMapping("/dm/send")
    public ResponseEntity<?> sendDmMessage(@RequestBody SendDmMsgParam sendDmMsgParam,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        sendDmMsgParam.setSenderId(userPrincipal.getUserId());
        return ResponseEntity.ok(dmMsgService.sendDmMsg(sendDmMsgParam));
    }

    // Get 1:1 (Direct Message) msg list
    @PostMapping(value = "/dm/msgs")
    public ResponseEntity<?> getDmMessages(@RequestBody ChatDmMsgParam chatDmMsgParam,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        chatDmMsgParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(dmMsgService.getDmMessages(chatDmMsgParam));
    }


}
