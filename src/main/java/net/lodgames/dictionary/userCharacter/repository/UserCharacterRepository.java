package net.lodgames.dictionary.userCharacter.repository;

import net.lodgames.dictionary.userCharacter.model.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCharacterRepository extends JpaRepository<UserCharacter, Long> {
    Optional<UserCharacter> findByIdAndUserId(Long id, Long userId);
    List<UserCharacter> findByUserId(Long userId);
}
