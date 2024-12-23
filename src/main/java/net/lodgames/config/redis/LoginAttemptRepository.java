package net.lodgames.config.redis;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@RedisHash("LoginAttempt")
@Repository
public interface LoginAttemptRepository extends CrudRepository<LoginAttempt,String > {

}
