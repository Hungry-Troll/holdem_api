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
public class FriendBlockService {
    private final UserRepository userRepository;
    private final FriendBlockQueryRepository friendBlockQueryRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendBlockRepository friendBlockRepository;

    // 친구차단 리스트
    public List<FriendBlockVo> friendBlocKList(FriendListParam friendListParam) {
        return friendBlockQueryRepository.selectFriendBlockByUserId(friendListParam, friendListParam.of());
    }

    // 친구차단 추가
    public void addFriendBlock(FriendBlockParam friendBlockParam) {
        long userId = friendBlockParam.getUserId();
        long friendId = friendBlockParam.getFriendId();
        // 상대방이 존재하는지 확인
        if (!userRepository.existsById(friendId)) {
            throw new RestException(ErrorCode.FAIL_ADD_FRIEND_BLOCK_NOT_EXIST);
        }

        // 이미 친구 차단이 있는지 확인
        if (friendBlockRepository.existsByUserIdAndFriendId(userId, friendId)) {
            throw new RestException(ErrorCode.FAIL_ADD_FRIEND_BLOCK_ALREADY_BLOCK);
        }

        // 친구 관계가 있으면 삭제
        deleteFriend(userId, friendId, false);
        deleteFriend(friendId, userId, false);
        // 친구 요청이 있으면 삭제
        deleteFriendRequest(userId, friendId);
        deleteFriendRequest(friendId, userId);
        // 친구 관계 외에 맺어진 관계가 있으면 삭제한다.(TODO) 기획상 정의 아직없음

        // 친구 차단 정보 추가
        friendBlockRepository.save(FriendBlock.builder()
                .userId(userId)
                .friendId(friendId)
                .build());
    }

    // 친구차단 삭제
    public void deleteFriendBlock(FriendBlockParam friendBlockParam) {
        FriendBlock friendBlock = retrieveFriendBlock(friendBlockParam.getUserId(), friendBlockParam.getFriendId());
        friendBlockRepository.delete(friendBlock);
    }

    // 친구차단 가져오기
    private FriendBlock retrieveFriendBlock(long userId, long friendId) {
        return friendBlockRepository.findByUserIdAndFriendId(userId, friendId).orElseThrow(() ->
                new RestException(ErrorCode.NOT_FOUND_FRIEND_BLOCK)
        );
    }

    // 친구 관계 삭제
    private void deleteFriend(long userId, long friendId, boolean isStrict) {
        Friend friend;
        try {
            friend = retrieveFriend(userId,friendId);
            friendRepository.delete(friend);
        } catch (RestException e){
            if (isStrict) {
                throw new RestException(ErrorCode.FAIL_DELETE_FRIEND);
            }
        }
    }

    // 친구차단 가져오기
    private Friend retrieveFriend(long userId, long friendId) {
        return friendRepository.findByUserIdAndFriendId(userId, friendId).orElseThrow(() ->
                new RestException(ErrorCode.NOT_FOUND_FRIEND)
        );
    }

    // 친구 요청 삭제
    private void deleteFriendRequest(long receiver, long sender) {
        friendRequestRepository.deleteByReceiverAndSender(receiver, sender);
    }

}

