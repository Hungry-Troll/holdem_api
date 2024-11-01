package net.lodgames.shop.purchase.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.shop.purchase.param.PurchaseListParam;
import net.lodgames.shop.purchase.vo.PurchaseVo;
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
class PurchaseQueryRepositoryTest {

    @Autowired
    private PurchaseQueryRepository purchaseQueryRepository;

    @Test
    void find_bundle_list_by_condition() {
        PurchaseListParam purchaseListParam = new PurchaseListParam();
        purchaseListParam.setUserId(1);
        List<PurchaseVo> purchaseVos = purchaseQueryRepository.getPurchaseList(purchaseListParam, purchaseListParam.of());
    }

    @Test
    void find_bundle_by_condition() {
        PurchaseVo purchaseVo = purchaseQueryRepository.getPurchase(1L);
    }
}