package net.lodgames.society.repository;

import net.lodgames.society.model.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocietyRepository extends JpaRepository<Society, Long> {
    Optional<Society> findById(long societyId);
}