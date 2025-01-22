package net.lodgames.relation.friend.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.relation.follow.repository.FollowRepository;
import net.lodgames.relation.friend.model.Friend;
import net.lodgames.relation.friend.model.FriendBlock;
import net.lodgames.relation.friend.param.FriendBlockParam;
import net.lodgames.relation.friend.param.FriendListParam;
import net.lodgames.relation.friend.repository.FriendBlockQueryRepository;
import net.lodgames.relation.friend.repository.FriendBlockRepository;
import net.lodgames.relation.friend.repository.FriendRepository;
import net.lodgames.relation.friend.repository.FriendRequestRepository;
import net.lodgames.relation.friend.vo.FriendBlockVo;
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
    private final FollowRepository followRepository;


    // 친구차단 리스트
    @Transactional(readOnly = true)
    public List<FriendBlockVo> friendBlockList(FriendListParam friendListParam) {
        return friendBlockQueryRepository.selectFriendBlockByUserId(friendListParam, friendListParam.of());
    }

    // 친구차단 추가
    @Transactional(rollbackFor = {Exception.class})
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
        deleteFriend(userId, friendId);
        deleteFriend(friendId, userId);
        // 친구 요청이 있으면 삭제
        deleteFriendRequest(userId, friendId);
        deleteFriendRequest(friendId, userId);
        // 친구 관계 외에 맺어진 관계가 있으면 삭제한다.(TODO) 기획상 정의 아직없음
        // 1. follow 삭제
        deleteFollows(userId, friendId);
        // 친구 차단 정보 추가
        friendBlockRepository.save(FriendBlock.builder()
                .userId(userId)
                .friendId(friendId)
                .build());
    }

    // 팔로우 취소
    private void deleteFollows(long userId, long friendId) {
        // 상대방이 follow 한 관계가 있으면 삭제
        deleteFollow(userId, friendId);
        // 내가 follow 한 관계까 있으면 삭제
        deleteFollow(friendId, userId);
    }

    // 친구 관계 삭제
    private void deleteFollow(long userId, long followerId) {
        followRepository.findByUserIdAndFollowId(userId, followerId)
                .ifPresent(followRepository::delete);
    }

    // 친구차단 삭제
    @Transactional(rollbackFor = {Exception.class})
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
    private void deleteFriend(long userId, long friendId) {
        friendRepository.findByUserIdAndFriendId(userId, friendId)
                .ifPresent(friendRepository::delete);
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

