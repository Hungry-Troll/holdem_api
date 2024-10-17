package net.lodgames.friend.service;

import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.friend.param.NoneFriendInfoParam;
import net.lodgames.friend.repository.NoneFriendQueryRepository;
import net.lodgames.friend.vo.NoneFriendInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoneFriendService {

    private final NoneFriendQueryRepository noneFriendQueryRepository;

    // 친구가 아닌 사용자 정보 조회
    public NoneFriendInfoVo getNoneFriendInfo(NoneFriendInfoParam param) {

        if (param.getUserId() == param.getNoneFriendId()) {
            throw new RestException(ErrorCode.FOUND_USER_IS_ME);
        }

        NoneFriendInfoVo noneFriendInfoVo = noneFriendQueryRepository.selectNoneFriendByNoneFriendId(param);
        if (noneFriendInfoVo == null) {
            throw new RestException(ErrorCode.NOT_FOUND_USER);
        }

        return noneFriendInfoVo;
    }
}
