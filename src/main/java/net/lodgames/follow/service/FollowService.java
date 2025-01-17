package net.lodgames.follow.service;

import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.follow.model.Follow;
import net.lodgames.follow.param.FollowListParam;
import net.lodgames.follow.param.FollowParam;
import net.lodgames.follow.param.UnFollowParam;
import net.lodgames.follow.repository.FollowQueryRepository;
import net.lodgames.follow.repository.FollowRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.follow.vo.FolloweeVo;
import net.lodgames.follow.vo.FollowerVo;
import net.lodgames.friend.repository.FriendBlockRepository;
import net.lodgames.user.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowQueryRepository followQueryRepository;
    private final FollowRepository followRepository;
    private final FriendBlockRepository friendBlockRepository;

    @Transactional(readOnly = true)
    // 팔로워 : 리스트 나를 팔로우 하고 있는 사람들
    public List<FollowerVo> follower(FollowListParam followListParam) {
        return followQueryRepository.selectFollowerByUserId(followListParam, followListParam.of());
    }

    @Transactional(readOnly = true)
    // 팔로잉 : 내가 팔로우 하고 있는 사람들
    public List<FolloweeVo> following(FollowListParam followListParam) {
        return followQueryRepository.selectFollowerByFollowId(followListParam, followListParam.of());
    }

    // 팔로우함
    @Transactional(rollbackFor = {Exception.class})
    public void follow(FollowParam followParam) {
        long userId = followParam.getUserId();
        long followId = followParam.getFollowId();
        // 존재하는 사람인지 확인
        if (!userRepository.existsByUserId(followId)) {
            throw new RestException(ErrorCode.FAIL_FOLLOW_TARGET_NOT_EXIST);
        }
        // 나 자신을 follow 할 수 없음
        if (userId == followId) {
            throw new RestException(ErrorCode.FAIL_NOT_ALLOWED_FOLLOW_SELF);
        }
        // 이미 follow 하고 있는 유저인지 확인
        if (followRepository.existsByUserIdAndFollowId(userId, followId)) {
            log.info("already followed user userId:" + userId + " - followId:" + followId);
            throw new RestException(ErrorCode.ALREADY_FOLLOWED_USER);
        }

        // 내가 상대방을 block 했는지 확인
        if (isBlocked(followId, userId)) {
            throw new RestException(ErrorCode.FAIL_FOLLOW_BLOCKED_FRIEND_BY_USER);
        }
        // 상대방이 나를 block 했는지 확인
        if (isBlocked(userId, followId)) {
            throw new RestException(ErrorCode.FAIL_FOLLOW_BLOCKED_USER_BY_FRIEND);
        }

        followRepository.save(Follow.builder()
                .userId(userId)
                .followId(followId)
                .build());
    }

    // 팔로우 취소
    @Transactional(rollbackFor = {Exception.class})
    public void unfollow(UnFollowParam unFollowParam) {
        Follow follow = retriveFollow(unFollowParam.getUserId(), unFollowParam.getFollowId());
        followRepository.delete(follow);
    }


    private Follow retriveFollow(long userId, long followId) {
        return followRepository.findByUserIdAndFollowId(userId, followId).orElseThrow(() ->
                new RestException(ErrorCode.FOLLOW_NOT_EXIST)
        );
    }


    // BLOCK 당했는지 확인
    public boolean isBlocked(long userId, long friendId) {
        return friendBlockRepository.existsByUserIdAndFriendId(userId, friendId);
    }
}
