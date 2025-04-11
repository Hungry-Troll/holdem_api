package net.lodgames.society.repository;

import jakarta.transaction.Transactional;
import net.lodgames.society.constants.JoinType;
import net.lodgames.society.constants.SocietyDummy;
import net.lodgames.society.model.Society;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SocietyRepositoryTest extends SocietyDummyTest {

    @Test
    @Rollback
    void save_society_then_society_added() {
        Society society = makeSociety1();
        Assertions.assertNotNull(society.getId());
        Assertions.assertEquals(SocietyDummy.SOCIETY_NAME_1, society.getName());
        Assertions.assertEquals(JoinType.FREE, society.getJoinType());
        Assertions.assertEquals(SocietyDummy.SOCIETY_INFO_1, society.getInfo());
        Assertions.assertEquals(SocietyDummy.SOCIETY_IMAGE_1, society.getImage());
        Assertions.assertEquals(SocietyDummy.SOCIETY_BACK_IMAGE_1, society.getBackImage());
        Assertions.assertEquals(SocietyDummy.SOCIETY_TAG_1, society.getTag());
        Assertions.assertNotNull(society.getCreatedAt());
        Assertions.assertNotNull(society.getUpdatedAt());
    }

    @Test
    @Rollback
    void update_society_then_society_updated() {
        Society society = makeSociety1();
        society.setName("Updated Name");
        society.setJoinType(JoinType.LOCK);
        society.setInfo("Updated Info");
        society.setImage("Updated Image");
        society.setBackImage("Updated Back Image");
        society.setTag("Updated Tag");
        Society updatedSociety = societyRepository.save(society);
        Assertions.assertEquals("Updated Name", updatedSociety.getName());
        Assertions.assertEquals(JoinType.LOCK, updatedSociety.getJoinType());
        Assertions.assertEquals("Updated Info", updatedSociety.getInfo());
        Assertions.assertEquals("Updated Image", updatedSociety.getImage());
        Assertions.assertEquals("Updated Back Image", updatedSociety.getBackImage());
        Assertions.assertEquals("Updated Tag", updatedSociety.getTag());
    }

    @Test
    @Rollback
    void delete_society_then_society_deleted() {
        Society society = makeSociety1();
        Long societyId = society.getId();
        societyRepository.delete(society);
        Assertions.assertFalse(societyRepository.findById(societyId).isPresent());
    }

    @Test
    @Rollback
    void find_by_id_then_society_found() {
        Society society = makeSociety1();
        Optional<Society> foundSocietyOptional = societyRepository.findById(society.getId());
        Assertions.assertTrue(foundSocietyOptional.isPresent());
        Assertions.assertEquals(society.getId(), foundSocietyOptional.get().getId());
    }

}