package net.lodgames.user.userblock.repository;

import net.lodgames.user.userblock.model.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {
    void deleteByUserIdAndBlockUserId(long userId, long blockId);
    boolean existsByUserIdAndBlockUserId(long userId, long blockUserId);
}
