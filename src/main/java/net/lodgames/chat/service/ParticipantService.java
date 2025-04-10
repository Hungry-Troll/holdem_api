package net.lodgames.chat.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.chat.constant.ParticipantType;
import net.lodgames.chat.constant.RoomType;
import net.lodgames.chat.model.ChatParticipant;
import net.lodgames.chat.model.ChatRoom;
import net.lodgames.chat.param.RoomInviteParam;
import net.lodgames.chat.param.RoomJoinParam;
import net.lodgames.chat.param.RoomKickParam;
import net.lodgames.chat.param.RoomLeaveParam;
import net.lodgames.chat.repository.ChatParticipantQueryRepository;
import net.lodgames.chat.repository.ChatParticipantRepository;
import net.lodgames.chat.repository.ChatRoomRepository;
import net.lodgames.chat.util.ChatRoomMapper;
import net.lodgames.chat.vo.ParticipantVo;
import net.lodgames.chat.vo.RoomParticipantsVo;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ParticipantService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatParticipantQueryRepository chatParticipantQueryRepository;
    private final ChatRoomMapper chatRoomMapper;

    // 해당 방에 속하는 방 정보 및 방 참가자 조회
    private RoomParticipantsVo selectRoomAndParticipant(long roomId) {
        // 방정보
        ChatRoom chatRoom = retrieveRoom(roomId);
        // 참가자 정보
        return getParticipantsInfoWithRoomInfo(chatRoom);
    }

    // 방에 속한 참가자를 조회하여 룸정보 및 참가자 정보를 반환한다
    public RoomParticipantsVo getParticipantsInfoWithRoomInfo(ChatRoom chatRoom) {
        RoomParticipantsVo roomParticipantsVo = chatRoomMapper.updateChatRoomToRoomParticipantVo(chatRoom);
        List<ParticipantVo> participantVos = chatParticipantQueryRepository.selectRoomParticipantByRoomId(roomParticipantsVo.getId());
        roomParticipantsVo.setParticipantVos(participantVos);
        return roomParticipantsVo;
    }

    // 참가자 생성
    public ChatParticipant createChatParticipant(long roomId, long userId, ParticipantType participantType) {
        return chatParticipantRepository.save(ChatParticipant.builder()
                .roomId(roomId)
                .userId(userId)
                .participantType(participantType)
                .build());
    }

    // * 방정보 취득
    private ChatRoom retrieveRoom(long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RestException(ErrorCode.CHAT_ROOM_NOT_EXIST));
    }

    // * 채팅방 초대
    @Transactional(rollbackFor = RestException.class)
    public RoomParticipantsVo inviteRoom(RoomInviteParam roomInviteParam) {
        long roomId = roomInviteParam.getRoomId();
        long userId = roomInviteParam.getUserId();
        // chat_room 정보 불러오기
        ChatRoom chatRoom = retrieveRoom(roomId);
        // 유저의 참가자 정보를 가져온다.
        ParticipantVo participantVo = chatParticipantQueryRepository.selectChatParticipantByRoomIdAndUserId(roomId, userId);
        // 방 참가자가 아니면 에러 발생
        if (participantVo == null) {
            throw new RestException(ErrorCode.FAIL_NOT_ROOM_MEMBER);
        }
        // 참가자 인원 조회
        Long participantsCnt = chatParticipantQueryRepository.countChatParticipantInRoom(roomId);
        // 정원 초과 여부 확인
        if (participantsCnt + roomInviteParam.getInviteList().size() > chatRoom.getCapacity()) {
            throw new RestException(ErrorCode.EXCEED_MAX_ROOM_CAPACITY);
        }
        // 이미 참여된 사람인지 확인하고 하나 이상 있으면 에러 발생( 전체 롤백)
        if (chatParticipantQueryRepository.countChatParticipantByRoomIdAndUserIdList(roomId, roomInviteParam.getInviteList()) > 0) {
            throw new RestException(ErrorCode.FAIL_INVITE_ALREADY_JOIN);
        }
        // 한명씩 초대
        for (Long participantId : roomInviteParam.getInviteList()) {
            createChatParticipant(roomId, participantId, ParticipantType.MEMBER);
            // TODO alarm to room members ( has join new member )
        }
        return selectRoomAndParticipant(roomId);
    }


    // * 채팅방 참여
    @Transactional(rollbackFor = RestException.class)
    public RoomParticipantsVo joinRoom(RoomJoinParam roomJoinParam) throws RestException {
        long roomId = roomJoinParam.getRoomId();
        if (isJoined(roomId, roomJoinParam.getUserId())) {
            throw new RestException(ErrorCode.FAIL_JOIN_ROOM_ALREADY_JOIN);
        }
        // 채팅룸 가져오기
        ChatRoom chatRoom = retrieveRoom(roomId);
        // 보안 코드 타입 일때 코드 일치 검사한다.
        if ((chatRoom.getRoomType() == RoomType.SECURE)
                && !chatRoom.getSecureCode().equals(roomJoinParam.getSecureCode())) {
            throw new RestException(ErrorCode.SECURE_CODE_NOT_MATCHED);
        }
        // 비공개 방일 경우 초대만 가능
        if (chatRoom.getRoomType() == RoomType.PRIVATE) {
            throw new RestException(ErrorCode.PRIVATE_ROOM_INVITE_ONLY);
        }
        // 현재 참여 인원 조회
        Long participantsCnt = chatParticipantQueryRepository.countChatParticipantInRoom(roomId);
        if (participantsCnt + 1 > chatRoom.getCapacity()) {
            throw new RestException(ErrorCode.EXCEED_MAX_ROOM_CAPACITY);
        }
        createChatParticipant(roomId, roomJoinParam.getUserId(), ParticipantType.MEMBER);
        return getParticipantsInfoWithRoomInfo(chatRoom);
    }

    public boolean isJoined(long roomId, long userId) {
        return chatParticipantRepository.existsByRoomIdAndUserId(roomId, userId);
    }

    // * 채팅방 나가기
    @Transactional(rollbackFor = RestException.class)
    public void leaveRoom(RoomLeaveParam roomLeaveParam) {
        long roomId = roomLeaveParam.getRoomId();
        // 방 조회
        ChatRoom chatRoom = retrieveRoom(roomId);
        // 참가자 수 조회
        Long participantsCnt = chatParticipantQueryRepository.countChatParticipantInRoom(roomId);
        if (participantsCnt == 1) {
            chatRoom.setDeletedAt(LocalDateTime.now());
            chatRoomRepository.save(chatRoom);
        }
        // 참가자 정보 삭제
        deleteChatParticipant(roomId, roomLeaveParam.getUserId());
        // TODO alarm to room members ( member has left)
    }

    // 채팅 참가자 삭제
    private void deleteChatParticipant(long roomId, long userId) {
        ChatParticipant chatParticipant = chatParticipantRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new RestException(ErrorCode.ROOM_PARTICIPANT_NOT_FOUND));
        chatParticipantRepository.delete(chatParticipant);
    }

    // * 채팅방 삭제
    @Transactional(rollbackFor = RestException.class)
    public void kickUserFromRoom(RoomKickParam roomKickParam) {
        long roomId = roomKickParam.getRoomId(); // 방 고유번호
        long userId = roomKickParam.getUserId(); // 유저 고유번호
        // 나 자신은 내보낼 수 없다.
        if (userId == roomKickParam.getTargetId()) {
            throw new RestException(ErrorCode.FAIL_CAN_NOT_KICK_MYSELF);
        }
        // 행위유저가 방 오너인지 확인
        checkUserIsOwner(roomId, userId);
        // 채팅 참가자 삭제
        deleteChatParticipant(roomId, roomKickParam.getTargetId());
    }

    // 방 오너인지 확인
    public void checkUserIsOwner(long roomId, long userId) {
        // 유저의 참가자 정보를 가져온다.
        ParticipantVo participantVo = chatParticipantQueryRepository.selectChatParticipantByRoomIdAndUserId(roomId, userId);
        // 방 참가자가 아니면 에러 발생
        if (participantVo == null) {
            throw new RestException(ErrorCode.FAIL_NOT_ROOM_MEMBER);
        }
        // 방 오너가 아니면 에러 발생
        if (participantVo.getParticipantType() != ParticipantType.OWNER) {
            throw new RestException(ErrorCode.NO_AUTH_MOD_ROOM);
        }
    }
}

