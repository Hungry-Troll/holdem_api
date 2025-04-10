package net.lodgames.chat.service;

import lombok.RequiredArgsConstructor;
import net.lodgames.chat.constant.DestType;
import net.lodgames.chat.constant.LeaveStatus;
import net.lodgames.chat.constant.TargetStatus;
import net.lodgames.chat.model.ChatDm;
import net.lodgames.chat.model.ChatMsg;
import net.lodgames.chat.param.ChatDmMsgParam;
import net.lodgames.chat.param.SendDmMsgParam;
import net.lodgames.chat.repository.ChatDmQueryRepository;
import net.lodgames.chat.repository.ChatDmRepository;
import net.lodgames.chat.repository.ChatMsgQueryRepository;
import net.lodgames.chat.repository.ChatMsgRepository;
import net.lodgames.chat.vo.ChatDmStatusVo;
import net.lodgames.chat.vo.DmSendResultVo;
import net.lodgames.chat.vo.MsgVo;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DmMsgService {
    private final ChatDmQueryRepository chatDmQueryRepository;
    private final ChatDmRepository chatDmRepository;
    private final ChatMsgQueryRepository chatMsgQueryRepository;
    private final ChatMsgRepository chatMsgRepository;


    // * send 1:1 message
    public DmSendResultVo sendDmMsg(SendDmMsgParam param) {
        Long senderId = param.getSenderId(); // 보내는 사람
        Long targetId = param.getTargetId(); // 받는 사람
        String fullType = param.getMsgType().getFullTypeString(); // type full String
        String msgBody = param.getMsgBody();
        return sendDm(senderId, targetId, fullType, msgBody);
    }

    private void checkDmStatusAndCreateIfNotExist(Long senderId, Long targetId) {
        // find dm status
        ChatDmStatusVo dmStatusVo = chatDmQueryRepository.selectChatDmStatus(senderId, targetId);
        if (dmStatusVo != null) {
            switch (dmStatusVo.getTargetStatus()) {
                case TargetStatus.NORMAL:
                    break;  // 정상
                case TargetStatus.BLOCK:          // 블럭됨
                    throw new RestException(ErrorCode.FAIL_SEND_MSG_BLOCKED);
                /*case TargetStatus.UNFRIEND:       // 친구 아님
                    throw new RestException(ErrorCode.FAIL_SEND_MSG_UNFRIEND);
                case TargetStatus.TARGET_STATUS_LEAVE:          // 친구 탈퇴함
                    throw new RestException(ErrorCode.FAIL_SEND_MSG_LEAVE);*/
            }
            // 친구
            if (dmStatusVo.getLeaveStatus() != LeaveStatus.STAY) {
                // update leave status
                updateToStay(senderId, targetId);
                // update leave status
                updateToStay(targetId, senderId);
            }
        } else {
            // TODO check friend status
            // TODO friend was blocked
            saveChatDm(senderId, targetId);
            saveChatDm(targetId, senderId);
        }
    }

    private void saveChatDm(Long senderId, Long targetId) {
        chatDmRepository.save(ChatDm.builder()
                .userId(senderId)
                .targetId(targetId)
                .targetStatus(TargetStatus.NORMAL)
                .leaveStatus(LeaveStatus.STAY)
                .build());
    }

    private void updateToStay(Long senderId, Long targetId) {
        ChatDm chatDm = chatDmRepository.findByUserIdAndTargetId(senderId, targetId).orElseThrow(
                () -> new RestException(ErrorCode.NOT_FOUND_DM)
        );
        chatDm.setLeaveStatus(LeaveStatus.STAY);
        chatDmRepository.save(chatDm);
    }

    // * get 1:1 message list
    public List<MsgVo> getDmMessages(ChatDmMsgParam chatDmMsgParam) {
        String dest = makeKeyStr(chatDmMsgParam.getUserId(), chatDmMsgParam.getTargetId()); // 1:1 dm destination key String
        chatDmMsgParam.setDest(dest);
        return chatMsgQueryRepository.selectChatMsgList(chatDmMsgParam, chatDmMsgParam.of());
    }

    // destination String ex) "lowIdx:largeIdx"
    public String makeKeyStr(long senderIdx, long targetIdx) {
        return (senderIdx < targetIdx) ?
                senderIdx + ":" + targetIdx :
                targetIdx + ":" + senderIdx;
    }

    // send MQ message
    private void sendMqMsg(String fullType, String dest, long senderId, long targetId, LocalDateTime sendTime) {
        // TODO Socket Notification
    }

    private DmSendResultVo sendDm(Long senderId, Long targetId, String fullType, String msgBody) {
        checkDmStatusAndCreateIfNotExist(senderId, targetId);
        // index key
        String keyStr = makeKeyStr(senderId, targetId);
        // sendTime
        LocalDateTime sendTime = LocalDateTime.now();
        // save to cass DB
        chatMsgRepository.save(ChatMsg.builder()
                .destType(DestType.DM) // destination type : 1 = 1:1(Direct Message)
                .dest(keyStr)          // destination String "low_userId:high_userId"
                .senderId(senderId)    // message sender userId
                .msgType(fullType)     // message Type
                .msgBody(msgBody)      // message body (defined by client)
                .sendTime(sendTime)    // sendTime
                .build());
        // send msg queue to target
        sendMqMsg(fullType, keyStr, senderId, targetId, sendTime);

        return DmSendResultVo.builder()
                .targetId(targetId)
                .sendTime(sendTime).build();
    }
}
