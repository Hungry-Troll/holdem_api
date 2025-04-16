package net.lodgames.message.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.message.model.Message;
import net.lodgames.message.param.*;
import net.lodgames.message.repository.MessageQueryRepository;
import net.lodgames.message.repository.MessageRepository;
import net.lodgames.message.util.MessageMapper;
import net.lodgames.message.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageQueryRepository messageQueryRepository;
    private final MessageMapper messageMapper;

    //쪽지 보내기
    @Transactional(rollbackFor = {Exception.class})
    public void addMessage(MessageAddParam messageAddParam) {
        if (messageAddParam.getReceiverId() <= 0) {
            throw new RestException(ErrorCode.FAIL_SEND_MESSAGE_NOT_FOUND_RECEIVER);
        }
        if (messageAddParam.getContent().isEmpty()) {
            throw new RestException(ErrorCode.FAIL_SEND_MESSAGE_NOT_FOUND_CONTENT);
        }

        //TODO 금칙어는 클라에서 되도록 처리하고 크리티컬한것만 서버에서 처리하도록 기능 추가
        messageRepository.save(Message.builder()
                .senderId(messageAddParam.getSenderId())
                .receiverId(messageAddParam.getReceiverId())
                .content(messageAddParam.getContent())
                .build()
        );
    }

    //쪽지 다중 보내기
    @Transactional(rollbackFor = {Exception.class})
    public void addMessages(MessagesAddParam messagesAddParam) {
        if (messagesAddParam.getReceiverIds().isEmpty()) {
            throw new RestException(ErrorCode.FAIL_SEND_MESSAGE_NOT_FOUND_RECEIVER);
        }
        if (messagesAddParam.getContent().isEmpty()) {
            throw new RestException(ErrorCode.FAIL_SEND_MESSAGE_NOT_FOUND_CONTENT);
        }

        //TODO 금칙어는 클라에서 되도록 처리하고 크리티컬한것만 서버에서 처리하도록 기능 추가
        for (int i = 0; i < messagesAddParam.getReceiverIds().size(); i++) {
            messageRepository.save(Message.builder()
                    .senderId(messagesAddParam.getSenderId())
                    .receiverId(messagesAddParam.getReceiverIds().get(i))
                    .content(messagesAddParam.getContent()).build()
            );
        }
    }

    //쪽지 읽기(읽음 처리)
    @Transactional(rollbackFor = {Exception.class})
    public MessageReadVo getMessage(MessageGetParam messageGetParam) {
        Message message = messageRepository.findByIdAndReceiverIdAndDeletedAtIsNull(
                messageGetParam.getMessageId(),
                messageGetParam.getReceiverId()
        ).orElseThrow(() -> new RestException(ErrorCode.FAIL_READ_MESSAGE_NOT_FOUND));
        message.setReadAt(LocalDateTime.now());
        return messageMapper.updateMessageToReadVo(messageRepository.save(message));
    }

    //쪽지 삭제
    @Transactional(rollbackFor = {Exception.class})
    public void deleteMessage(MessageDeleteParam messageDeleteParam) {
        Message message = messageRepository.findByIdAndReceiverIdAndDeletedAtIsNull(
                messageDeleteParam.getMessageId(),
                messageDeleteParam.getReceiverId()
        ).orElseThrow(() -> new RestException(ErrorCode.FAIL_DELETE_MESSAGE_NOT_FOUND));
        message.setDeletedAt(LocalDateTime.now());
        messageRepository.save(message);
    }

    //쪽지 다중 삭제
    @Transactional(rollbackFor = {Exception.class})
    public void deleteMessages(MessagesDeleteParam messagesDeleteParam) {
        List<Message> message = messageRepository.findByIdInAndReceiverIdAndDeletedAtIsNull(
                messagesDeleteParam.getMessageIds(),
                messagesDeleteParam.getReceiverId()
        );
        // 찾은 쪽지가 없는 경우
        if (message.isEmpty()) {
            throw new RestException(ErrorCode.FAIL_DELETE_MESSAGE_NOT_FOUND);
        }
        message.forEach(one -> one.setDeletedAt(LocalDateTime.now()));
        messageRepository.saveAll(message);
    }

    //보낸 쪽지 수정
    @Transactional(rollbackFor = {Exception.class})
    public MessageModVo modMessage(MessageModParam messageModParam) {
        Message findMessage = messageRepository.findByIdAndReadAtIsNullAndDeletedAtIsNull(messageModParam.getMessageId()
        ).orElseThrow(() -> new RestException(ErrorCode.FAIL_UPDATE_MESSAGE_NOT_FOUND));
        findMessage.setContent(messageModParam.getContent());
        findMessage.setCreatedAt(LocalDateTime.now()); // 새로운 생성시간 (받는 사람이 읽지 않았으므로...)
        return messageMapper.updateMessageToModVo(messageRepository.save(findMessage));
    }

    //받은 쪽지함
    @Transactional(readOnly = true)
    public List<MessageBoxVo> receiveBoxMessage(MessageReceiveBoxParam messageReceiveBoxParam) {
        return messageQueryRepository.getMessageBoxesByReceiver(messageReceiveBoxParam, messageReceiveBoxParam.of());
    }

    //보낸 쪽지함
    @Transactional(readOnly = true)
    public List<MessageBoxVo> sendBoxMessage(MessageSendBoxParam messageSendBoxParam) {
        return messageQueryRepository.getMessageBoxesBySender(messageSendBoxParam, messageSendBoxParam.of());
    }
}
