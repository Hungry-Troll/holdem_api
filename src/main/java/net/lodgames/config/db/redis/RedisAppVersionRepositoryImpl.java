package net.lodgames.config.db.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class RedisAppVersionRepositoryImpl {
    private static final String KEY_APP_VERSION = "app:version";

    private final RedisTemplate<String, String> versionRedisTemplate;

    private final SetOperations<String, String> setOperations;

    public RedisAppVersionRepositoryImpl(@Qualifier("versionRedisTemplate") RedisTemplate<String, String> versionRedisTemplate) {
        this.versionRedisTemplate = versionRedisTemplate;
        this.setOperations = versionRedisTemplate.opsForSet();
    }

    public String getAppVersion() {
        return versionRedisTemplate.opsForValue().get(KEY_APP_VERSION);
    }

    public boolean setAppVersion(String versionInfo) {
        return setValueByKey(KEY_APP_VERSION,versionInfo);
    }

    public boolean deleteAppVersion() {
        return deleteValueByKey(KEY_APP_VERSION);
    }

    private boolean setValueByKey(String key, String value) {
        try {
            versionRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return false;
    }

    private boolean deleteValueByKey(String key) {
        try {
            return Boolean.TRUE.equals(versionRedisTemplate.delete(key));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return false;
    }
}
