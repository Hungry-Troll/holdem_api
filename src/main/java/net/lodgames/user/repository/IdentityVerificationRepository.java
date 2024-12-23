package net.lodgames.user.repository;


import net.lodgames.user.model.IdentityVerification;
import net.lodgames.user.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentityVerificationRepository extends JpaRepository<IdentityVerification, Long> {
    Optional<IdentityVerification> findByUserId(Long userId);
}
