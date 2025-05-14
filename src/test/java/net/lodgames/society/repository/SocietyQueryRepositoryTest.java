package net.lodgames.society.repository;

import jakarta.transaction.Transactional;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.society.constants.SocietyDummy;
import net.lodgames.society.model.Society;
import net.lodgames.society.param.SocietyListParam;
import net.lodgames.society.service.SocietyServiceDummy;
import net.lodgames.society.vo.SocietyInfoVo;
import net.lodgames.society.vo.SocietyVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@Import(QueryDsLTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SocietyQueryRepositoryTest extends SocietyServiceDummy {

    @Autowired
    private SocietyQueryRepository stuffQueryRepository;
    @Autowired
    private SocietyRepository stuffRepository;

    @Test
    @Rollback
    void find_society_list_by_user() {
        // 모임1
        Society society = makeDummySociety();
        stuffRepository.save(society);
        // 모임2
        Society society2 = makeDummySociety2();
        stuffRepository.save(society2);
        // 모임 리스트 조회 파라미터
        SocietyListParam societyListParam = new SocietyListParam();
        societyListParam.setUserId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID);
        // 모임 리스트 조회
        List<SocietyVo> societyVos =  stuffQueryRepository.findSocietyByUserIdAndMemberType(societyListParam, societyListParam.of());
        // 모임 리스트가 비어있지 않음
        Assertions.assertNotNull(societyVos);
        // 모임 리스트의 사이즈가 2개 이상 (기존에 들어가 있는 경우도 있음)
        Assertions.assertTrue(societyVos.size() >= 2);

    }

    @Test
    @Rollback
    void find_society_list_by_society_id() {
        // 모임
        Society society = makeDummySociety();
        stuffRepository.save(society);
        Long societyId = society.getId(); // 모임 ID
        // 특정 모임 조회
        SocietyInfoVo societyInfoVo = stuffQueryRepository.findSocietyBySocietyId(societyId);
        // 모임 정보가 비어있지 않음
        Assertions.assertNotNull(societyInfoVo);
        // 모임 정보의 ID가 일치함
        Assertions.assertEquals(societyId, societyInfoVo.getId());
        // 모임 정보의 이름이 일치함
        Assertions.assertEquals(SocietyDummy.SOCIETY_NAME_1, societyInfoVo.getName());
    }

}