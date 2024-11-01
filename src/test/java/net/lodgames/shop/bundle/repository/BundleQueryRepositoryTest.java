package net.lodgames.shop.bundle.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.shop.bundle.constants.BundleSearchType;
import net.lodgames.shop.bundle.param.BundleListParam;
import net.lodgames.shop.bundle.vo.BundleVo;
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
class BundleQueryRepositoryTest {

    @Autowired
    private BundleQueryRepository bundleQueryRepository;

    @Test
    void find_bundle_list_by_condition() {
        BundleListParam bundleListParam = new BundleListParam();
        bundleListParam.setSearchType(BundleSearchType.NAME);
        bundleListParam.setSearchValue("카드");
        bundleListParam.setPage(1);
        List<BundleVo> bundleVoList = bundleQueryRepository.getBundleList(bundleListParam, bundleListParam.of());
        for(BundleVo bundleVo : bundleVoList) {
            log.info(bundleVo.toString());
        }
    }

   /* @Test
    void find_bundle_by_condition() {
        BundleItemCurrencyVo bundleItemCurrencyVo = bundleQueryRepository.getBundleItemCurrency(2);
        log.debug(bundleItemCurrencyVo.toString());
    }*/
}