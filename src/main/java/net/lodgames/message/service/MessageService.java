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
    @Transactional
    public void addMessage(MessageAddParam messageAddParam) {
        if (messageAddParam.getReceiverId() <= 0) {
            throw new RestException(ErrorCode.FAIL_SEND_MESSAGE_NOT_FOUND_RECEIVER);
        }
        //TODO 금칙어는 클라에서 되도록 처리하고 크리티컬한것만 서버에서 처리하도록 기능 추가
        messageRepository.save(Message.builder()
                .senderId(messageAddParam.getSenderId())
                .receiverId(messageAddParam.getReceiverId())
                .content(messageAddParam.getContent()).build()
        );
    }

    //쪽지 읽기
    @Transactional
    public MessageReadVo getMessage(MessageGetParam messageGetParam) {
        Message findMessage = messageRepository.findByIdAndReceiverIdAndDeletedAtIsNull(
                messageGetParam.getMessageId(),
                messageGetParam.getReceiverId()
        ).orElseThrow(() -> new RestException(ErrorCode.FAIL_READ_MESSAGE_NOT_FOUND));
        findMessage.setReadAt(LocalDateTime.now());
        return messageMapper.updateReadMessageToVo(messageRepository.save(findMessage));
    }

    //쪽지 삭제
    @Transactional
    public void deleteMessage(MessageDeleteParam messageDeleteParam) {
        Message findMessage = messageRepository.findByIdAndReceiverIdAndDeletedAtIsNull(
                messageDeleteParam.getMessageId(),
                messageDeleteParam.getReceiverId()
        ).orElseThrow(() -> new RestException(ErrorCode.FAIL_DELETE_MESSAGE_NOT_FOUND));
        findMessage.setDeletedAt(LocalDateTime.now());
        messageRepository.save(findMessage);
    }

    //보낸 쪽지 수정
    @Transactional
    public MessageUpdateVo updateMessage(MessageUpdateParam messageUpdateParam) {
        Message findMessage = messageRepository.findByIdAndReadAtIsNullAndDeletedAtIsNull(messageUpdateParam.getMessageId()
        ).orElseThrow(() -> new RestException(ErrorCode.FAIL_UPDATE_MESSAGE_NOT_FOUND));
        if (findMessage.getReadAt() != null) {
            throw new RestException(ErrorCode.FAIL_UPDATE_MESSAGE_ALREADY_READ);
        }
        if (findMessage.getDeletedAt() != null) {
            throw new RestException(ErrorCode.FAIL_UPDATE_MESSAGE_ALREADY_DELETED);
        }
        findMessage.setContent(messageUpdateParam.getContent());
        findMessage.setCreatedAt(LocalDateTime.now()); // 새로운 생성시간 (받는 사람이 읽지 않았으므로...)
        return messageMapper.updateMessageToVo(messageRepository.save(findMessage));
    }

    //받은 쪽지함
    @Transactional
    public List<MessageReceiveBoxVo> receiveBoxMessage(MessageReceiveBoxParam messageReceiveBoxParam) {
        List<Message> findMessages = messageQueryRepository.findReceivedBoxMessage(
                messageReceiveBoxParam, messageReceiveBoxParam.of());
        return messageMapper.updateMessageListToVoList(findMessages);
    }

    //보낸 쪽지함
    @Transactional
    public List<MessageSendBoxVo> sendBoxMessage(MessageSendBoxParam messageSendBoxParam) {
        List<Message> findMessages = messageQueryRepository.findSendBoxMessage(
                messageSendBoxParam, messageSendBoxParam.of());
        return messageMapper.updateCheckSendMessageToVo(findMessages);
    }
}
