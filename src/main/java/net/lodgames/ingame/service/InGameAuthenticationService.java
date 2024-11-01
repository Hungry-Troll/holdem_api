package net.lodgames.ingame.service;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.redis.RedisInGameAuthenticationRepositoryImpl;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.ingame.param.InGameAuthenticationKeyParam;
import net.lodgames.ingame.vo.InGameAuthenticatedKeyVo;
import net.lodgames.ingame.vo.InGameAuthenticationKeyVo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor
public class InGameAuthenticationService {

    private final RedisInGameAuthenticationRepositoryImpl redisInGameAuthenticationRepository;

    private final int SHORT_ID_LENGTH = 8;
    private final String SAP = ":";

    public InGameAuthenticationKeyVo createTemporaryAuthenticationKey(Long userId) {
        String keyValue = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
        redisInGameAuthenticationRepository.setInGameAuthentication(keyValue, userId + SAP + "addinfo");
        return new InGameAuthenticationKeyVo(keyValue);
    }

    public InGameAuthenticatedKeyVo authenticateTemporaryAuthenticationKey(
            InGameAuthenticationKeyParam inGameAuthenticationKeyParam) {
        String authenticationKey = inGameAuthenticationKeyParam.getAuthenticationKey();
        validateInGameAuthenticationKeyParam(authenticationKey);
        String value = redisInGameAuthenticationRepository.getValidationCode(authenticationKey);
        // 임시 인증키 인증이 완료되면 관련 데이터 삭제
        redisInGameAuthenticationRepository.deleteValidationCode(authenticationKey);
        return InGameAuthenticatedKeyVo.builder().value(value).build();
    }

    private void validateInGameAuthenticationKeyParam(String authenticationKey) {
        if (!StringUtils.hasText(authenticationKey)) {
            throw new RestException(ErrorCode.INGAME_AUTHENTICATION_KEY_REQUIRED_PARAMETER);
        }
    }
}
