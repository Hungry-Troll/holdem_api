package net.lodgames.society.repository;

import net.lodgames.society.model.SocietyMemberWait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocietyMemberWaitRepository extends JpaRepository<SocietyMemberWait, Long> {
    Optional<SocietyMemberWait> findBySocietyIdAndUserId(long societyId, long userId);
    List<SocietyMemberWait> removeBySocietyId(long societyId);
    boolean existsSocietyMemberWaitBySocietyIdAndUserId(long societyId, long userId);
}