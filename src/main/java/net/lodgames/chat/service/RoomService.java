package net.lodgames.chat.service;


import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.chat.constant.ParticipantType;
import net.lodgames.chat.constant.RoomType;
import net.lodgames.chat.model.ChatRoom;
import net.lodgames.chat.param.*;
import net.lodgames.chat.repository.ChatRoomQueryRepository;
import net.lodgames.chat.repository.ChatRoomRepository;
import net.lodgames.chat.util.ChatParticipantsMapper;
import net.lodgames.chat.util.ChatRoomMapper;
import net.lodgames.chat.vo.RoomParticipantsVo;
import net.lodgames.chat.vo.RoomVo;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class RoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomQueryRepository chatRoomQueryRepository;
    private final ParticipantService participantService;
    private final ChatRoomMapper chatRoomMapper;
    private final ChatParticipantsMapper chatParticipantsMapper;
    private final static int ROOM_CAPACITY_BASIC = 50;
    private final static int MINIMUM_INVITE_NUM = 2;
    private final static int OWNER_ONLY = 1;
    private final static int ROOM_ID_CODE_LENGTH = 10;

    // * 전체 채팅방 조회
    @Transactional(readOnly = true)
    public List<RoomVo> getRoomList(RoomListParam roomListParam) {
        return chatRoomQueryRepository.selectRooms(roomListParam, roomListParam.of());
    }

    // * 나의 채팅방 리스트 조회
    @Transactional(readOnly = true)
    public List<RoomVo> getParticipatedRoomList(RoomListParam roomListParam) {
        return chatRoomQueryRepository.selectRoomsByUserId(roomListParam, roomListParam.of());
    }

    // * 채팅방 단일 정보 조회 (참가자 전용) (채팅방 + 참가자 정보)
    @Transactional(readOnly = true)
    public RoomParticipantsVo getRoomInfo(RoomInfoParam roomInfoParam) {
        // 방 참가자인지 확인
        if (!participantService.isJoined(roomInfoParam.getRoomId(), roomInfoParam.getUserId())) {
            throw new RestException(ErrorCode.FAIL_NOT_ROOM_MEMBER);
        }
        return selectRoomAndParticipant(roomInfoParam.getRoomId());
    }

    // 해당 방에 속하는 방 정보 및 방 참가자 조회
    private RoomParticipantsVo selectRoomAndParticipant(long roomId) {
        // 방정보
        ChatRoom chatRoom = retrieveRoom(roomId);
        // 참가자 정보
        return participantService.getParticipantsInfoWithRoomInfo(chatRoom);
    }


    // * 채팅방 생성 ( 빈방 )
    @Transactional(rollbackFor = RestException.class)
    public RoomParticipantsVo addRoom(RoomAddParam roomAddParam) {
        // 방을 생성한다. (오너 방 참여 동시 진행)
        RoomParticipantsVo roomParticipantsVo = addRoom(roomAddParam.getRoomType(), roomAddParam.getName(), OWNER_ONLY, roomAddParam.getSecureCode());
        // 참가자 생성 (생성자 = 오너)
        roomParticipantsVo.addParticipantVo( // 참가자 정보 리스트에 추가
                chatParticipantsMapper.updateChatParticipantToParticipantVo( // entity -> vo 변환
                        // 참가자 생성 및 저장 ( 오너 )
                        participantService.createChatParticipant(roomParticipantsVo.getId(), roomAddParam.getUserId(), ParticipantType.OWNER)));
        return roomParticipantsVo;
    }

    // * 채팅방 생성 ( 단체 )
    @Transactional(rollbackFor = RestException.class)
    public RoomParticipantsVo addGroupRoom(RoomGroupAddParam roomGroupAddParam) {
        // 그룹 채팅방 생성 최소 인원보다 많은지 확인(2명)
        int participantSize = roomGroupAddParam.getInviteList().size();
        if (participantSize < MINIMUM_INVITE_NUM) { // 방생성 최소인원 보다 적으면
            throw new RestException(ErrorCode.FAIL_CREATE_ROOM_MINIMUM_INVITE_NUM);
        }
        // 기본 정원 보다 많은지 확인
        if (participantSize > ROOM_CAPACITY_BASIC) { // 기본 방생성 정원 보다 많으면
            throw new RestException(ErrorCode.EXCEED_MAX_ROOM_CAPACITY);
        }
        // 방 생성
        RoomParticipantsVo roomParticipantsVo = addRoom(roomGroupAddParam.getRoomType(), roomGroupAddParam.getName(), participantSize, roomGroupAddParam.getSecureCode());
        // 참가자 생성 (생성자 = 오너)
        roomParticipantsVo.addParticipantVo( // 참가자 정보 리스트에 추가
                chatParticipantsMapper.updateChatParticipantToParticipantVo( // entity -> vo 변환
                        // 참가자 생성 및 저장 ( 오너 )
                        participantService.createChatParticipant(roomParticipantsVo.getId(), roomGroupAddParam.getUserId(), ParticipantType.OWNER)));
        // 참가자 생성
        for (Long inviteTargetId : roomGroupAddParam.getInviteList()) {
            // TODO Block 당한 친구일 경우 어떻게 할것인지 확인!!
            roomParticipantsVo.addParticipantVo( // 참가자 정보 리스트에 추가
                    chatParticipantsMapper.updateChatParticipantToParticipantVo(// entity -> vo 변환
                            // 참가자 생성 및 저장 ( 오너 )
                            participantService.createChatParticipant(roomParticipantsVo.getId(), inviteTargetId, ParticipantType.MEMBER)));
        }
        return roomParticipantsVo;
    }

    // 방 생성
    private RoomParticipantsVo addRoom(RoomType roomType, String name, int userNum, String secureCode) {
        ChatRoom.ChatRoomBuilder chatRoomBuilder = ChatRoom.builder()
                .name(name)              // 방 이름
                .roomType(roomType)      // 방 타입
                .currentUserNum(userNum) // 현재 유저 수
                .idCode(generateAlphanumericRandomString(ROOM_ID_CODE_LENGTH)) // ID 코드
                .capacity(ROOM_CAPACITY_BASIC); // 방 정원 (기본값)
        // 잠금 타입인 경우 보안코드 입력
        if (RoomType.SECURE == roomType) {
            chatRoomBuilder.secureCode(secureCode);
        }
        return chatRoomMapper.updateChatRoomToRoomParticipantVo(chatRoomRepository.save(chatRoomBuilder.build()));
    }

    // 정해진 숫자만큼 알파벳 대소문자 및 숫자로 임의의 문자를 만든다.
    public String generateAlphanumericRandomString(int targetStringLength) {
        int leftLimit = 48;           // numeral '0'
        int rightLimit = 122;         // letter 'z'
        Random random = new Random(); // random

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // * 방정보 변경
    @Transactional(rollbackFor = RestException.class)
    public RoomParticipantsVo modRoom(RoomModParam roomModParam) {
        Long roomId = roomModParam.getRoomId();
        Long userId = roomModParam.getUserId();

        // Validate if the user is the owner of the room
        participantService.checkUserIsOwner(roomId, userId);

        // Retrieve the room information
        ChatRoom chatRoom = retrieveRoom(roomId);

        // Update room name , capacity if provided
        updateRoomNameAndCapacity(chatRoom, roomModParam.getName(), roomModParam.getCapacity());

        // Update room type and secure code if provided
        updateRoomTypeAndSecureCode(chatRoom, roomModParam.getRoomType(), roomModParam.getSecureCode());

        // Save the updated room information
        chatRoomRepository.save(chatRoom);

        return participantService.getParticipantsInfoWithRoomInfo(chatRoom);
    }

    // Update room name , capacity if provided
    private void updateRoomNameAndCapacity(ChatRoom chatRoom, String name, Integer capacity) {
        if (StringUtils.isNotEmpty(name)) {
            chatRoom.setName(name);
        }
        if (capacity != null) {
            chatRoom.setCapacity(capacity);
        }
    }

    // Update room type and secure code if provided
    private void updateRoomTypeAndSecureCode(ChatRoom chatRoom, RoomType roomType, String secureCode) {
        if (roomType == null || roomType == chatRoom.getRoomType()) {
            return;
        }
        if (roomType == RoomType.SECURE) {
            if (StringUtils.isEmpty(secureCode)) {
                throw new RestException(ErrorCode.SECURE_TYPE_ROOM_NEED_SECURE_CODE);
            }
            chatRoom.setSecureCode(secureCode);
        } else {
            chatRoom.setSecureCode(null);
        }
        chatRoom.setRoomType(roomType);
    }

    // * 방정보 취득
    private ChatRoom retrieveRoom(long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RestException(ErrorCode.CHAT_ROOM_NOT_EXIST));
    }
}

