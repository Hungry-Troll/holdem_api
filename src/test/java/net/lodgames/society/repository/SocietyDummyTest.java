package net.lodgames.society.repository;

import jakarta.transaction.Transactional;
import net.lodgames.society.constants.JoinType;
import net.lodgames.society.constants.MemberType;
import net.lodgames.society.constants.SocietyDummy;
import net.lodgames.society.model.Society;
import net.lodgames.society.model.SocietyMember;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static net.lodgames.society.constants.SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID;


@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SocietyDummyTest {

    @Autowired
    protected SocietyRepository societyRepository;

    @Autowired
    private SocietyMemberRepository societyMemberRepository;

    public Society makeSociety1() {
        Society society = Society.builder()
                .name(SocietyDummy.SOCIETY_NAME_1)
                .joinType(JoinType.FREE)
                .info(SocietyDummy.SOCIETY_INFO_1)
                .image(SocietyDummy.SOCIETY_IMAGE_1)
                .backImage(SocietyDummy.SOCIETY_BACK_IMAGE_1)
                .tag(SocietyDummy.SOCIETY_TAG_1)
                .build();
        societyRepository.save(society);
        return society;
    }

    public Society makeSociety2() {
        Society society = Society.builder()
                .name(SocietyDummy.SOCIETY_NAME_2)
                .joinType(JoinType.LOCK)
                .passcode(SocietyDummy.SOCIETY_PASSCODE)
                .info(SocietyDummy.SOCIETY_INFO_2)
                .image(SocietyDummy.SOCIETY_IMAGE_2)
                .backImage(SocietyDummy.SOCIETY_BACK_IMAGE_2)
                .tag(SocietyDummy.SOCIETY_TAG_2)
                .build();
        societyRepository.save(society);
        return society;
    }

    public Society makeSociety3() {
        Society society = Society.builder()
                .name(SocietyDummy.SOCIETY_NAME_3)
                .joinType(JoinType.PERMIT)
                .info(SocietyDummy.SOCIETY_INFO_3)
                .image(SocietyDummy.SOCIETY_IMAGE_3)
                .backImage(SocietyDummy.SOCIETY_BACK_IMAGE_3)
                .tag(SocietyDummy.SOCIETY_TAG_3)
                .build();
        societyRepository.save(society);
        return society;
    }

    public SocietyMember makeSocietyMember(Long societyId , Long memberId, MemberType memberType) {
        if(societyId == null) {
            societyId = SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID;
        }

        if(memberId == null) {
            memberId = SocietyDummy.SOCIETY_MEMBER_USER_ID;
        }

        SocietyMember societyMember = SocietyMember.builder()
                .societyId(societyId)
                .userId(memberId)
                .memberType(MemberType.LEADER)
                .build();
        societyMemberRepository.save(societyMember);
        return societyMember;
    }

}
