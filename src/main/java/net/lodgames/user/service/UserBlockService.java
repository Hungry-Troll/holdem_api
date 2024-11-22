package net.lodgames.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.user.model.UserBlock;
import net.lodgames.user.param.UserBlockDeleteParam;
import net.lodgames.user.param.UserBlockListParam;
import net.lodgames.user.param.UserBlockParam;
import net.lodgames.user.repository.UserBlockQueryRepository;
import net.lodgames.user.repository.UserBlockRepository;
import net.lodgames.user.repository.UserRepository;
import net.lodgames.user.vo.UserBlockListVo;
import net.lodgames.user.vo.UserBlockVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserBlockService {

    private final UserRepository userRepository;
    private final UserBlockRepository userBlockRepository;
    private final UserBlockQueryRepository userBlockQueryRepository;

    // 유저 차단
    public void blockUser(UserBlockParam userBlockParam) {
        // 차단할 유저 존재하는지 확인
        if (!userRepository.existsById(userBlockParam.getBlockUserId())) {
            throw new RestException(ErrorCode.FAIL_USER_BLOCK_NOT_EXIST);
        }
        // 자기 자신 차단 x
        if (userBlockParam.getUserId() == userBlockParam.getBlockUserId()) {
            throw new RestException(ErrorCode.FOUND_USER_IS_ME);
        }
        // 이미 차단 되었는지 확인
        UserBlockVo userBlockVo = userBlockQueryRepository.blockUser(userBlockParam);
        if (userBlockVo != null) {
            throw new RestException(ErrorCode.EXIST_USER_BLOCK);        // 이미 차단된 상태라면 로직 종료
        }

        // 차단 로직 실행
        userBlockRepository.save(UserBlock.builder()
                .userId(userBlockParam.getUserId())
                .blockId(userBlockParam.getBlockUserId()).build());
    }

    // 유저 차단 리스트
    public List<UserBlockListVo> blockUserList(UserBlockListParam userBlockListParam) {
        return userBlockQueryRepository.selectBlockUserList(userBlockListParam, userBlockListParam.of());
    }

    // 차단 유저 차단 해제
    public void deleteBlockUser(UserBlockDeleteParam userBlockParam) {

        Long userId = userBlockParam.getUserId();
        Long blockUserId = userBlockParam.getBlockUserId();

        // 유저 차단 확인
        if (!userBlockRepository.existsByUserIdAndBlockUserId(userId, blockUserId)) {
            throw new RestException(ErrorCode.NOT_EXIST_USER_BLOCK);
        }

        userBlockRepository.deleteByUserIdAndBlockUserId(userId, blockUserId);
    }
}
