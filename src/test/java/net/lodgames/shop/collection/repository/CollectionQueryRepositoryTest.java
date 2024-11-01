package net.lodgames.shop.collection.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.shop.collection.param.CollectionListParam;
import net.lodgames.shop.collection.vo.CollectionVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@Import(QueryDsLTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class CollectionQueryRepositoryTest {

    @Autowired
    private CollectionQueryRepository collectionQueryRepository;

    @Test
    void find_collect_list_by_condition() {
        CollectionListParam collectionListParam = new CollectionListParam();
        List<CollectionVo> CollectionVos = collectionQueryRepository.getCollectionList(collectionListParam, collectionListParam.of());
        for (CollectionVo collectionVo : CollectionVos) {
            log.info(collectionVo.toString());
        }
    }

}