package net.lodgames.relation.friend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.event.constant.UserEventType;
import net.lodgames.event.service.UserEventService;
import net.lodgames.relation.friend.model.Friend;
import net.lodgames.relation.friend.param.*;
import net.lodgames.relation.friend.repository.FriendBlockRepository;
import net.lodgames.relation.friend.repository.FriendQueryRepository;
import net.lodgames.relation.friend.repository.FriendRepository;
import net.lodgames.relation.friend.vo.FriendInfoVo;
import net.lodgames.relation.friend.vo.FriendSearchVo;
import net.lodgames.relation.friend.vo.FriendVo;
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
    private final FriendBlockRepository friendBlockRepository;
    private final UserEventService userEventService;

    // 친구 리스트
    @Transactional(rollbackFor = {Exception.class})
    public List<FriendVo> getFriendList(FriendListParam friendListParam) {
        return friendQueryRepository.selectFriendByUserId(friendListParam, friendListParam.of());
    }

    // 친구 정보 조회
    @Transactional(rollbackFor = {Exception.class})
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


    // 친구 관계 삭제
    @Transactional(rollbackFor = {Exception.class})
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

    // 친구 전체 숫자
    @Transactional(rollbackFor = {Exception.class})
    public long getFriendCount(long userId) {
        return friendRepository.countByUserId(userId);
    }

    // 친구 닉네임 검색
    @Transactional(rollbackFor = {Exception.class})
    public List<FriendSearchVo> searchFriend(FriendSearchParam friendSearchParam) {
        return friendQueryRepository.selectFriendByNickName(friendSearchParam, friendSearchParam.of());
    }


    // 친구 관계인지 확인
    public boolean isFriend(long userId, long friendId) {
        return friendRepository.existsByUserIdAndFriendId(userId, friendId);
    }

    // BLOCK 당했는지 확인
    public boolean isBlocked(long userId, long friendId) {
        return friendBlockRepository.existsByUserIdAndFriendId(userId, friendId);
    }
}

