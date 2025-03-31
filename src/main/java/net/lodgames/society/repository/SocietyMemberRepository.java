package net.lodgames.society.repository;

import net.lodgames.society.constants.MemberType;
import net.lodgames.society.model.SocietyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocietyMemberRepository extends JpaRepository<SocietyMember, Long> {
    List<SocietyMember> removeBySocietyId(long societyId);
    Optional<SocietyMember> findBySocietyIdAndUserId(long societyId, long userId);
    int countByUserIdAndMemberType(long userId, MemberType type);
    boolean existsSocietyMemberBySocietyIdAndUserId(long societyId, long userId);
}
