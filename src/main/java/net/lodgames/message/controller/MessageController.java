package net.lodgames.message.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.message.param.*;
import net.lodgames.message.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class MessageController {

    private final MessageService messageService;

    //쪽지 보내기
    @PostMapping("/messages/{receiverId}/send")
    public ResponseEntity<?> addMessage(@PathVariable(name="receiverId") long receiverId,
                                         @RequestBody MessageAddParam messageAddParam,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageAddParam.setReceiverId(receiverId);
        messageAddParam.setSenderId(userPrincipal.getUserId());
        return ResponseEntity.ok(messageService.addMessage(messageAddParam));
    }

    //쪽지 읽기
    @GetMapping("/messages/{messageId}/read")
    public ResponseEntity<?> getMessage(@PathVariable(name="messageId") long messageId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(messageService.getMessage(MessageGetParam.builder()
                                                                            .receiverId(userPrincipal.getUserId())
                                                                            .messageId(messageId)
                                                                            .build()));
    }

    //받은 쪽지함 읽기
    @PostMapping("messages/receivedBox/read")
    public ResponseEntity<?> postReceivedBoxMessageRead(@RequestBody MessageReceivedBoxGetParam messageReceivedBoxGetParam,
                                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageReceivedBoxGetParam.setReceiverId(userPrincipal.getUserId());
        return ResponseEntity.ok(messageService.postReceivedBoxMessageRead(messageReceivedBoxGetParam));
    }

    //쪽지 삭제
    @DeleteMapping("messages/{messageId}/delete")
    public ResponseEntity<?> deleteMessage(@PathVariable(name="messageId") long messageId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(messageService.deleteMessage(MessageDeleteParam.builder()
                                                                                .receiverId(userPrincipal.getUserId())
                                                                                .messageId(messageId)
                                                                                .build()));
    }

    //쪽지 수정
    @PostMapping("messages/{messageId}/update")
    public ResponseEntity<?> updateMessage(@PathVariable long messageId,
                                           @RequestBody MessageUpdateParam messageUpdateParam,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageUpdateParam.setMessageId(messageId);
        messageUpdateParam.setSenderId(userPrincipal.getUserId());
        return ResponseEntity.ok(messageService.updateMessage(messageUpdateParam));
    }

    //보낸 쪽지함 읽기
    @PostMapping("messages/sentBox/read")
    public ResponseEntity<?> postSentBoxMessageRead(@RequestBody MessageSentBoxGetParam messageSentBoxGetParam,
                                                    @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageSentBoxGetParam.setSenderId(userPrincipal.getUserId());
        return ResponseEntity.ok(messageService.postSentBoxMessageRead(messageSentBoxGetParam));
    }
}
