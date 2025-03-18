package net.lodgames.relation.block.repository;

import net.lodgames.relation.block.model.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {
    void deleteByUserIdAndBlockUserId(Long userId, Long blockId);
    boolean existsByUserIdAndBlockUserId(Long userId, Long blockUserId);
}
