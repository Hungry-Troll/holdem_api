package net.lodgames.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.user.param.SearchUserNicknameParam;
import net.lodgames.user.param.UserInfoParam;
import net.lodgames.user.repository.UserQueryRepository;
import net.lodgames.user.vo.FindUserNicknameVo;
import net.lodgames.user.vo.UserInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserQueryRepository userQueryRepository;

    @Transactional(readOnly = true)
    public UserInfoVo userInfo(UserInfoParam userInfoParam) {
        // 자기 자신을 조회
        if (userInfoParam.getUserId() == userInfoParam.getTargetUserId()) {
            throw new RestException(ErrorCode.FOUND_USER_IS_ME);
        }
        // 유저 정보를 찾을 수 없음
        UserInfoVo userInfoVo = userQueryRepository.selectUserInfo(userInfoParam);
        if (userInfoVo == null) {
            throw new RestException(ErrorCode.NOT_FOUND_USER_INFO);
        }
        return userInfoVo;
    }

    // 친구 추가를 위한 유저 닉네임으로 찾기
    @Transactional(readOnly = true)
    public List<FindUserNicknameVo> searchUserNickname(SearchUserNicknameParam searchUserNicknameParam) {
        if (searchUserNicknameParam.getNickname() == null || searchUserNicknameParam.getNickname().trim().isEmpty()) {
            throw new RestException(ErrorCode.INVALID_NICKNAME_WHITESPACE);
        }
        return userQueryRepository.searchUserNickname(searchUserNicknameParam, searchUserNicknameParam.of());
    }
}
