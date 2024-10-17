package net.lodgames.config.db.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;


@Repository
@Slf4j
public class RedisInGameAuthenticationRepositoryImpl {

    private final long KEY_EXPIRED_SECONDS = 60;  // 60 seconds = 1 minute
    private final String KEY_IN_GAME_PREFIX = "ingame:";

    private final RedisTemplate<String, String> inGameRedisTemplate;
    private final ValueOperations<String, String> setOperations;

    public RedisInGameAuthenticationRepositoryImpl(@Qualifier("inGameRedisTemplate") RedisTemplate<String, String> inGameRedisTemplate) {
        this.inGameRedisTemplate = inGameRedisTemplate;
        this.inGameRedisTemplate.setKeySerializer(new StringRedisSerializer());
        this.inGameRedisTemplate.setValueSerializer(new StringRedisSerializer());
        this.setOperations = inGameRedisTemplate.opsForValue();
    }

    public void setInGameAuthentication(String keyValue, String addInfo) {
        setOperations.set(KEY_IN_GAME_PREFIX + keyValue, addInfo, KEY_EXPIRED_SECONDS, TimeUnit.SECONDS);
    }

    public String getValidationCode(String keValue) {
        return setOperations.get(KEY_IN_GAME_PREFIX + keValue);
    }

    public void deleteValidationCode(String validationCode) {
        inGameRedisTemplate.delete(validationCode);
    }

}
