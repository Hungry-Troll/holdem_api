package net.lodgames.character.repository;

import net.lodgames.character.model.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCharacterRepository extends JpaRepository<UserCharacter, Long> {
    Optional<UserCharacter> findByIdAndUserId(Long id, Long userId);
}
