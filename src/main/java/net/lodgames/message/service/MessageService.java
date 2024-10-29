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
    public MessageSendVo addMessage(MessageAddParam messageAddParam) {
        if (messageAddParam.getReceiverId() == 0) {
            throw new RestException(ErrorCode.FAIL_SEND_MESSAGE_NOT_FOUND_RECEIVER);
        }
        Message message = messageRepository.save(Message.builder()
                .senderId(messageAddParam.getSenderId())
                .receiverId(messageAddParam.getReceiverId())
                .content(messageAddParam.getContent()).build()
        );
        return messageMapper.updateSendMessageToVo(message);
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

    //받은 쪽지함 읽기
    @Transactional
    public List<MessageReadVo> postReceivedBoxMessageRead(MessageReceivedBoxGetParam messageReceivedBoxGetParam) {
        List<Message> findMessages = messageQueryRepository.findReceivedBoxMessage(messageReceivedBoxGetParam, messageReceivedBoxGetParam.of()).orElseThrow(()->
                new RestException(ErrorCode.FAIL_READ_MESSAGE_NOT_FOUND));

        return messageMapper.updateMessageInBoxReadParamToVoList(findMessages);
    }

    //쪽지 삭제
    @Transactional
    public MessageDeleteVo deleteMessage(MessageDeleteParam messageDeleteParam) {
        Message findMessage = messageRepository.findByIdAndReceiverIdAndDeletedAtIsNull(
                messageDeleteParam.getMessageId(),
                messageDeleteParam.getReceiverId()
        ).orElseThrow(() -> new RestException(ErrorCode.FAIL_DELETE_MESSAGE_NOT_FOUND));
        findMessage.setDeletedAt(LocalDateTime.now());
        return messageMapper.updateDeleteMessageToVo(messageRepository.save(findMessage));
    }

    //보낸 쪽지 수정
    @Transactional
    public MessageUpdateVo updateMessage(MessageUpdateParam messageUpdateParam) {
        Message findMessage = messageRepository.findByIdAndCreatedAtIsNotNullAndReadAtIsNullAndDeletedAtIsNull(
                messageUpdateParam.getMessageId()
        ).orElseThrow(() -> new RestException(ErrorCode.FAIL_UPDATE_MESSAGE_NOT_FOUND));
        findMessage.setContent(messageUpdateParam.getContent());
        return messageMapper.updateMessageToVo(messageRepository.save(findMessage));
    }

    //보낸 쪽지함 확인
    @Transactional
    public List<MessageSentCheckVo> postSentBoxMessageRead(MessageSentBoxGetParam messageSentBoxGetParam) {
        List<Message> findMessageList = messageQueryRepository.findSendBoxMessage(messageSentBoxGetParam, messageSentBoxGetParam.of()).orElseThrow(()->
                new RestException(ErrorCode.FAIL_SEND_MESSAGE_NOT_FOUND_RECEIVER));

        return messageMapper.updateCheckSendMessageToVo(findMessageList);
    }
}
