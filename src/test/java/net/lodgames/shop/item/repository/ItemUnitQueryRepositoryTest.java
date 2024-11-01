package net.lodgames.shop.item.repository;

import jakarta.transaction.Transactional;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.shop.item.constants.ItemUnitSearchType;
import net.lodgames.shop.item.param.ItemUnitListParam;
import net.lodgames.shop.item.vo.ItemUnitVo;
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
class ItemUnitQueryRepositoryTest {

    @Autowired
    private ItemUnitQueryRepository itemUnitQueryRepository;

    @Test
    void FIND_ITEM_LIST_BY_CONDITION() {
        ItemUnitListParam itemUnitListParam = new ItemUnitListParam();
        itemUnitListParam.setSearchType(ItemUnitSearchType.NAME);
        itemUnitListParam.setSearchValue("재화");
        itemUnitListParam.setPage(1);
        List<ItemUnitVo> itemVoList = itemUnitQueryRepository.getItemUnitList(itemUnitListParam, itemUnitListParam.of());
    }
}