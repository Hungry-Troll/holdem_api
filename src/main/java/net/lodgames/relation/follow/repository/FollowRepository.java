package net.lodgames.relation.follow.repository;

import net.lodgames.relation.follow.model.Follow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends CrudRepository<Follow, Long> {
    Optional<Follow> findByUserIdAndFollowId(long accountId, long followId);
    boolean existsByUserIdAndFollowId(long userId, long followId);
}
