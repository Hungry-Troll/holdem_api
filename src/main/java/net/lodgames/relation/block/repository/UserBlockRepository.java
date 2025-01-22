package net.lodgames.relation.block.repository;

import net.lodgames.relation.block.model.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {
    void deleteByUserIdAndBlockUserId(long userId, long blockId);
    boolean existsByUserIdAndBlockUserId(long userId, long blockUserId);
}
