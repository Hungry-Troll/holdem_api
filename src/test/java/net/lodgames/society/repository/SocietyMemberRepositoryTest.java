package net.lodgames.society.repository;

import jakarta.transaction.Transactional;
import net.lodgames.society.constants.MemberType;
import net.lodgames.society.constants.SocietyDummy;
import net.lodgames.society.model.SocietyMember;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SocietyMemberRepositoryTest extends SocietyDummyTest {

    @Test
    @Rollback
    public void save_society_member_then_society_member_added() {
        SocietyMember societyMember = makeSocietyMember(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID, SocietyDummy.SOCIETY_MEMBER_USER_ID, MemberType.LEADER);
        Assertions.assertNotNull(societyMember.getId());
        Assertions.assertEquals(SocietyDummy.SOCIETY_MEMBER_USER_ID, societyMember.getUserId());
        Assertions.assertEquals(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID, societyMember.getSocietyId());
        Assertions.assertEquals(MemberType.LEADER, societyMember.getMemberType());
        Assertions.assertNotNull(societyMember.getCreatedAt());
        Assertions.assertNotNull(societyMember.getUpdatedAt());
    }


}
