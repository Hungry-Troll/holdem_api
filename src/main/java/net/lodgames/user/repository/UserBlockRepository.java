package net.lodgames.user.repository;

import net.lodgames.user.model.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {
    void deleteByUserIdAndBlockUserId(long userId, long blockId);
    boolean existsByUserIdAndBlockUserId(long userId, long blockUserId);
}
