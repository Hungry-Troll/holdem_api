package net.lodgames.chat.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.chat.constant.LeaveStatus;
import net.lodgames.chat.constant.TargetStatus;
import net.lodgames.chat.model.ChatDm;
import net.lodgames.chat.param.DmAddParam;
import net.lodgames.chat.param.DmInfoParam;
import net.lodgames.chat.param.DmListParam;
import net.lodgames.chat.repository.ChatDmQueryRepository;
import net.lodgames.chat.repository.ChatDmRepository;
import net.lodgames.chat.vo.DmVo;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.relation.friend.service.FriendService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DmService {
    private final ChatDmQueryRepository chatDmQueryRepository;
    private final FriendService friendService;
    private final ChatDmRepository chatDmRepository;

    /** 1:1 채팅방 리스트 조회 */
    public List<DmVo> getDmList(DmListParam dmListParam) {
        return chatDmQueryRepository.selectChatDmListByUserId(dmListParam, dmListParam.of());
    }

    /** 1:1 채팅방 정보 조회 */
    public DmVo getDmInfo(DmInfoParam dmInfoParam) {
        DmVo dmVo = chatDmQueryRepository.selectChatDmByUserId(dmInfoParam);
        if (dmVo == null) {
            throw new RestException(ErrorCode.FAIL_GET_DM_NOT_EXIST);
        }
        return dmVo;
    }

    /** 채팅방 생성 ( 개인 ) */
    @Transactional(rollbackFor = {Exception.class})
    public DmVo addDm(DmAddParam dmAddParam) {
        long userId = dmAddParam.getUserId();     // userId
        long targetId = dmAddParam.getTargetId(); // target userId

        // 대상인지 확인 ( 친구에게만 보내는 것이 아니면 삭제 한다.)
        /*
        if (!friendService.isFriend(userId, targetId)) {
            throw new RestException(ErrorCode.FAIL_ADD_DM_NOT_FRIEND);
        }
        */
        // 대상로 부터 block 당했는지 확인
        if (friendService.isBlocked(userId, targetId)) {
            throw new RestException(ErrorCode.FAIL_ADD_ROOM_BLOCK_BY_FRIEND);
        }
        // 내가 대상에게 보내는 DM 설정
        ChatDm chatDm = upsertDm(userId, targetId);
        // 대상가 나에게 보내는 DM 설정
        upsertDm(targetId, userId);
        // result
        DmVo dmVo = new DmVo();
        BeanUtils.copyProperties(chatDm, dmVo);
        return dmVo;
    }

    // update DM to stay if exist or create new DM
    private ChatDm upsertDm(long userId, long targetId) {
        // DM 관계 있는지 확인
        ChatDm chatDm = chatDmRepository.findByUserIdAndTargetId(userId, targetId)
                .orElseGet(() ->
                        // create
                        chatDmRepository.save(ChatDm.builder()
                                .userId(userId)
                                .targetId(targetId)
                                .targetStatus(TargetStatus.NORMAL)
                                .leaveStatus(LeaveStatus.STAY)
                                .build())
                );
        if (chatDm.getLeaveStatus() == LeaveStatus.LEAVE) {
            chatDm.setLeaveStatus(LeaveStatus.STAY);
            // update stay
            chatDmRepository.save(chatDm);
        }
        return chatDm;
    }

    // destination String ex) "lowId:largeId"
    public String makeKeyStr(long senderId, long targetId) {
        return (senderId < targetId) ?
                senderId + ":" + targetId :
                targetId + ":" + senderId;
    }

}
