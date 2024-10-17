package net.lodgames.friend.service;


import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.event.constant.UserEventType;
import net.lodgames.event.service.UserEventService;
import net.lodgames.friend.model.FriendBlock;
import net.lodgames.friend.model.Friend;
import net.lodgames.friend.model.FriendRequest;
import net.lodgames.friend.param.*;
import net.lodgames.friend.repository.*;
import net.lodgames.friend.vo.*;
import net.lodgames.user.constants.UserStatus;
import net.lodgames.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FriendService {
    private static final String SOURCE_FRIEND_DELETE = "/api/friend/deleteFriend";
    private final FriendQueryRepository friendQueryRepository;
    private final FriendRepository friendRepository;
    private final UserEventService userEventService;

    // 친구 리스트
    public List<FriendVo> getFriendList(FriendListParam friendListParam) {
        return friendQueryRepository.selectFriendByUserId(friendListParam, friendListParam.of());
    }

    // 친구 정보 조회
    public FriendInfoVo getFriendInfo(FriendInfoParam param) {

        // 자기 자신을 조회
        if (param.getUserId() == param.getFriendId()) {
            throw new RestException(ErrorCode.FOUND_USER_IS_ME);
        }

        FriendInfoVo friendInfoVo = friendQueryRepository.selectFriendByFriendId(param);
        if (friendInfoVo == null) {
            throw new RestException(ErrorCode.NOT_FOUND_FRIEND);
        }

        return friendInfoVo;
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

    // 친구 추가를 위한 유저 찾기
    public List<FindFriendVo> findFriend(FindFriendParam findFriendParam) {
        // to lowercase
        if (findFriendParam.getId() == null || findFriendParam.getId().isEmpty()) {
            findFriendParam.setNickname(findFriendParam.getNickname().toLowerCase());
            return friendQueryRepository.selectFriendByNickname(findFriendParam, findFriendParam.of());
        } else {
            findFriendParam.setId(findFriendParam.getId().toLowerCase());
            FindFriendVo findFriendVo = friendQueryRepository.findFriendById(findFriendParam);
            if (findFriendVo == null || findFriendVo.getUserId() == null) {
                throw new RestException(ErrorCode.NOT_FOUND_USER);
            }
            // 나자신 에게는 보낼수 없음
            if (findFriendVo.getUserId() == findFriendParam.getUserId()) {
                throw new RestException(ErrorCode.FOUND_USER_IS_ME);
            }
            return List.of(findFriendVo);
        }
    }

    // 친구 관계 삭제
    public void deleteFriend(FriendDeleteParam friendDeleteParam) {
        long userId = friendDeleteParam.getUserId();
        long friendId = friendDeleteParam.getFriendId();
        // 친구 관계가 있으면 삭제
        deleteFriend(userId, friendId, true);
        deleteFriend(friendId, userId, true);

        // 친구 삭제 알림
        String extData = userEventService.getSimpleExtData(userId);
        userEventService.setUserEvent(SOURCE_FRIEND_DELETE, UserEventType.FRIEND_DELETE, userId, friendId, extData);
    }

}

