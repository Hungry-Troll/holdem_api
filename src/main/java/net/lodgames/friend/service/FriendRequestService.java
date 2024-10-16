package net.lodgames.friend.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.event.constant.UserEventType;
import net.lodgames.event.service.UserEventService;
import net.lodgames.friend.model.Friend;
import net.lodgames.friend.model.FriendBlock;
import net.lodgames.friend.model.FriendRequest;
import net.lodgames.friend.param.*;
import net.lodgames.friend.repository.*;
import net.lodgames.friend.vo.*;
import net.lodgames.user.constants.UserStatus;
import net.lodgames.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FriendRequestService {
    private static final String SOURCE_FRIEND_ACCEPT = "/api/friend/acceptFriendRequest";
    private static final String SOURCE_FRIEND_REQUEST = "/api/friend/addFriendRequest";
    private final UserRepository userRepository;
    private final FriendRequestQueryRepository friendRequestQueryRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendBlockRepository friendBlockRepository;
    private final UserEventService userEventService;

    // 친구 요청 전송 리스트
    public List<FriendReqSendVo> friendRequestSendList(FriendListParam friendListParam) {
        return friendRequestQueryRepository.selectFriendRequestByFriendId(friendListParam, friendListParam.of());
    }

    // 친구요청 받은 리스트
    public List<FriendReqRecvVo> friendRequestList(FriendListParam friendListParam) {
        return friendRequestQueryRepository.selectFriendRequestByUserId(friendListParam, friendListParam.of());
    }

    /**
     * 친구요청 등록
     * 주의 : 친구 요청을 보내면 받는 입장에서는 주체가 바뀐다.
     * request : sender = userId , receiver = friendId
     * request DB : userId = receiver = friendId(req) , friendId = sender = userId(req)
     *
     * @param friendRequestParam 친구요청 정보
     */
    @Transactional(rollbackFor = Exception.class)
    public void addFriendRequest(FriendRequestParam friendRequestParam) {
        long sender = friendRequestParam.getSender();
        long receiver = friendRequestParam.getReceiver();

        // 나자신에게는 보낼수 없음
        if (sender == receiver) {
            throw new RestException(ErrorCode.FAIL_ADD_FRIEND_REQUEST_NOT_ALLOWED_SELF);
        }

        // 이미 친구 요청이 있는지 확인
        if (friendRequestRepository.existsByReceiverAndSender(receiver, sender)) {
            throw new RestException(ErrorCode.FAIL_ADD_FRIEND_REQUEST_ALREADY_SENT);
        }

        // 상대방이 존재하는지 확인
        if (!userRepository.existsByUserIdAndStatusLessThanEqual(receiver, UserStatus.LOGOUT)) {
            throw new RestException(ErrorCode.FAIL_REQUEST_TARGET_NOT_EXIST); // FAIL_REQUEST_TARGET_NOT_EXIST
        }
        // 이미 친구 관계 인지 확인
        if (isFriend(sender, receiver)) {
            throw new RestException(ErrorCode.FAIL_ADD_FRIEND_REQUEST_ALREADY_FRIEND);
        }

        // 상대방이 나를 block 했는지 확인
        if (isBlocked(receiver, sender)) {
            throw new RestException(ErrorCode.FAIL_ADD_FRIEND_REQUEST_BLOCKED_USER_BY_FRIEND);
        }

        // 내가 상대방을 block 했는지 확인
        if (isBlocked(sender, receiver)) {
            throw new RestException(ErrorCode.FAIL_ADD_FRIEND_REQUEST_BLOCKED_FRIEND_BY_USER);
        }

        // 친구가 나에게 이미 친구가 나를 추가했는지 확인 만약 있다면 친구관계로 변경
        boolean isAdded = deleteRequestAndAddFriends(sender, receiver);
        if (isAdded) {
            return;
        }

        // 친구 요청 정보 저장
        friendRequestRepository.save(FriendRequest.builder()
                .receiver(receiver)
                .sender(sender).build());

        // 친구 요청 알림
        String extData = userEventService.getSimpleExtData(sender);
        userEventService.setUserEvent(SOURCE_FRIEND_REQUEST, UserEventType.FRIEND_REQUEST, sender, receiver, extData);
    }


    // 친구 요청 수락
    public void acceptFriendRequest(FriendRequestParam friendRequestParam) {
        long receiver = friendRequestParam.getReceiver();
        long sender = friendRequestParam.getSender();
        boolean isAdded = deleteRequestAndAddFriends(receiver, sender);
        if (!isAdded) {
            throw new RestException(ErrorCode.FAIL_ACCEPT_FRIEND_REQUEST);
        }
        // 친구 수락 알림
        String extData = userEventService.getSimpleExtData(receiver);
        userEventService.setUserEvent(SOURCE_FRIEND_ACCEPT, UserEventType.FRIEND_ACCEPT, receiver, sender, extData);
    }

    // 친구요청 삭제
    public void deleteFriendRequest(FriendRequestParam friendRequestParam) {
        FriendRequest friendRequest = retrieveFriendRequest(friendRequestParam.getReceiver(), friendRequestParam.getSender());
        friendRequestRepository.delete(friendRequest);
    }

    // 친구 요청 가져오기
    private FriendRequest retrieveFriendRequest(long receiver, long sender) {
        return friendRequestRepository.findByReceiverAndSender(receiver, sender).orElseThrow(() ->
                new RestException(ErrorCode.NOT_FOUND_FRIEND_REQUEST)
        );
    }

    // 친구 요청 정보 삭제 및 친구 관계 설정
    private boolean deleteRequestAndAddFriends(long receiver, long sender) {
        return friendRequestRepository.findByReceiverAndSender(receiver, sender)
                .map(friendRequest -> {
                    friendRequestRepository.delete(friendRequest);
                    addFriend(sender, receiver); // Add friend relationship both ways
                    addFriend(receiver, sender);
                    return true;
                })
                .orElse(false);
    }

    // 친구 관계인지 확인
    public boolean isFriend(long userId, long friendId) {
        return friendRepository.existsByUserIdAndFriendId(userId, friendId);
    }

    // BLOCK 당했는지 확인
    public boolean isBlocked(long userId, long friendId) {
        return friendBlockRepository.existsByUserIdAndFriendId(userId, friendId);
    }

    // 친구 등록
    private void addFriend(long userId, long friendId) {
        friendRepository.save(Friend.builder()
                .userId(userId)
                        .type(1)
                .friendId(friendId).build());
    }

}

