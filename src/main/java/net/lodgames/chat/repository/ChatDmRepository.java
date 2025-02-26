package net.lodgames.chat.repository;

import net.lodgames.chat.model.ChatDm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ChatDmRepository extends CrudRepository<ChatDm, Long> {
    Optional<ChatDm> findByUserIdAndTargetId(long userId, long targetId);
}
