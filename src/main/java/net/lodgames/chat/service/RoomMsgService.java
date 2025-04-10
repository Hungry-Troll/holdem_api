package net.lodgames.chat.service;

import lombok.RequiredArgsConstructor;
import net.lodgames.chat.constant.DestType;
import net.lodgames.chat.model.ChatMsg;
import net.lodgames.chat.param.ChatMsgParam;
import net.lodgames.chat.param.SendMsgParam;
import net.lodgames.chat.repository.ChatMsgQueryRepository;
import net.lodgames.chat.repository.ChatMsgRepository;
import net.lodgames.chat.repository.ChatParticipantQueryRepository;
import net.lodgames.chat.vo.MsgVo;
import net.lodgames.chat.vo.ParticipantVo;
import net.lodgames.chat.vo.SendResultVo;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomMsgService {
    private final ChatMsgQueryRepository chatMsgQueryRepository;
    private final ChatMsgRepository chatMsgRepository;
    private final ChatParticipantQueryRepository chatParticipantQueryRepository;

    // * send group message
    public SendResultVo sendGroupMsg(SendMsgParam param) {
        long senderId = param.getSenderId();
        long roomId = param.getDestId();
        checkParticipantOfRoom(roomId, senderId);
        String dest = String.valueOf(roomId);
        // sendTime
        LocalDateTime sendTime = LocalDateTime.now();
        String msgType = param.getMsgType().getFullTypeString();
        // save to cass DB
        chatMsgRepository.save(ChatMsg.builder()
                .destType(DestType.ROOM)        // destination type : 2 = Room Message
                .dest(dest)                     // destination String roomId
                .senderId(senderId)             // message sender userId
                .msgType(msgType)               // message Type
                .msgBody(param.getMsgBody())    // message body (defined by client)
                .sendTime(sendTime)             // sendTime
                .build());
        // send msg queue to target
        sendMqMsg(param.getMsgType().getFullTypeString(), dest, param.getSenderId(), sendTime);

        return SendResultVo.builder()
                .destId(roomId)
                .sendTime(sendTime).build();
    }

    // * get group message list
    public List<MsgVo> getGroupMessage(ChatMsgParam chatMsgParam) {
        checkParticipantOfRoom(Long.parseLong(chatMsgParam.getDest()), chatMsgParam.getUserId());
        return chatMsgQueryRepository.selectGroupMsgList(chatMsgParam, chatMsgParam.of());
    }

    private void checkParticipantOfRoom(long roomId, long userId) {
        // 유저의 참가자 정보를 가져온다.
        ParticipantVo participantVo = chatParticipantQueryRepository.selectChatParticipantByRoomIdAndUserId(roomId, userId);
        // 방 참가자가 아니면 에러 발생
        if (participantVo == null) {
            throw new RestException(ErrorCode.FAIL_NOT_ROOM_MEMBER);
        }
    }

    // send MQ message
    private void sendMqMsg(String fullType, String dest, long senderId, LocalDateTime sendTime) {
        // TODO Socket Notification
    }
}