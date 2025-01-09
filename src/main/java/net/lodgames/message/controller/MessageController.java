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
    @PostMapping("/messages")
    public ResponseEntity<?> addMessage(@RequestBody MessageAddParam messageAddParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageAddParam.setSenderId(userPrincipal.getUserId());
        messageService.addMessage(messageAddParam);
        return ResponseEntity.ok().build();
    }

    //쪽지 다중 보내기
    @PostMapping("/messages/multiple")
    public ResponseEntity<?> addMessages(@RequestBody MessagesAddParam messagesAddParam,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messagesAddParam.setSenderId(userPrincipal.getUserId());
        messageService.addMessages(messagesAddParam);
        return ResponseEntity.ok().build();
    }

    //쪽지 읽기
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable(name="messageId") long messageId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(
                messageService.getMessage(MessageGetParam.builder()
                                                         .receiverId(userPrincipal.getUserId())
                                                         .messageId(messageId)
                                                         .build()));
    }

    //쪽지 단일 삭제
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable(name="messageId") long messageId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageService.deleteMessage(MessageDeleteParam.builder()
                                                       .receiverId(userPrincipal.getUserId())
                                                       .messageId(messageId)
                                                       .build());
        return ResponseEntity.ok().build();
    }

    //쪽지 다중 삭제
    @DeleteMapping("/messages")
    public ResponseEntity<?> deleteMessages(@RequestBody MessagesDeleteParam messagesDeleteParam,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messagesDeleteParam.setReceiverId(userPrincipal.getUserId());
        messageService.deleteMessages(messagesDeleteParam);
        return ResponseEntity.ok().build();
    }

    //쪽지 수정 // 보낸 쪽지를 상대방이 읽거나 삭제 전까지만 수정 할 수 있음
    @PutMapping("/messages/{messageId}")
    public ResponseEntity<?> modMessage(@PathVariable(name="messageId") long messageId,
                                        @RequestBody MessageModParam messageModParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageModParam.setMessageId(messageId);
        messageModParam.setSenderId(userPrincipal.getUserId());
        return ResponseEntity.ok(messageService.modMessage(messageModParam));
    }

    //받은 쪽지함 //
    @GetMapping("/messages/receiveBox")
    public ResponseEntity<?> receiveBoxMessage(@RequestBody MessageReceiveBoxParam messageReceiveBoxParam,
                                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageReceiveBoxParam.setReceiverId(userPrincipal.getUserId());
        return ResponseEntity.ok(messageService.receiveBoxMessage(messageReceiveBoxParam));
    }

    //보낸 쪽지함 // 보낸 쪽지함은 상대방이 읽기 전이나 삭제 전까지만 볼 수 있음
    @GetMapping("/messages/sendBox")
    public ResponseEntity<?> sendBoxMessage(@RequestBody MessageSendBoxParam messageSendBoxParam,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageSendBoxParam.setSenderId(userPrincipal.getUserId());
        return ResponseEntity.ok(messageService.sendBoxMessage(messageSendBoxParam));
    }
}
