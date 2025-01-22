package net.lodgames.relation.memo.repository;

import net.lodgames.relation.memo.model.UserMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMemoRepository extends JpaRepository<UserMemo, Long> {
    Optional<UserMemo> findByUserIdAndTargetUserId(long userId, long targetUserId);
}
