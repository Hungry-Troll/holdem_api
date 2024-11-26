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

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserQueryRepository userQueryRepository;

    public UserInfoVo userInfo(UserInfoParam userInfoParam) {
        // 자기 자신을 조회
        if (userInfoParam.getUserId() == userInfoParam.getTargetUserId()) {
            throw new RestException(ErrorCode.FOUND_USER_IS_ME);
        }

        UserInfoVo userInfoVo = userQueryRepository.selectUserInfo(userInfoParam);
        if (userInfoVo == null) {
            throw new RestException(ErrorCode.NOT_FOUND_USERINFO);
        }
        return userInfoVo;
    }

    // 친구 추가를 위한 유저 닉네임으로 찾기
    public List<FindUserNicknameVo> searchUserNickname(SearchUserNicknameParam searchUserNicknameParam) {
        if (searchUserNicknameParam.getNickname() == null || searchUserNicknameParam.getNickname().trim().isEmpty()) {
            throw new RestException(ErrorCode.NOT_FOUND_NICKNAME_USER);
        }
        return userQueryRepository.searchUserNickname(searchUserNicknameParam, searchUserNicknameParam.of());
    }
}
