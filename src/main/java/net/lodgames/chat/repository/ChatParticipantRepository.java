package net.lodgames.chat.repository;

import net.lodgames.chat.model.ChatParticipant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends CrudRepository<ChatParticipant, Long> {
    Optional<ChatParticipant> findByUserIdAndRoomId(Long userId, Long roomId);
    boolean existsByRoomIdAndUserId(long roomId, long userId);
}
