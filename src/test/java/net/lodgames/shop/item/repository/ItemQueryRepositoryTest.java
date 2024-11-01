package net.lodgames.shop.item.repository;

import jakarta.transaction.Transactional;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.shop.item.constants.ItemSearchType;
import net.lodgames.shop.item.param.ItemListParam;
import net.lodgames.shop.item.vo.ItemVo;
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
class ItemQueryRepositoryTest {

    @Autowired
    private ItemQueryRepository itemQueryRepository;

    @Test
    void FIND_ITEM_LIST_BY_CONDITION() {
        ItemListParam itemListParam = new ItemListParam();
        itemListParam.setSearchType(ItemSearchType.NAME);
        itemListParam.setSearchValue("재화");
        itemListParam.setPage(1);
        itemListParam.setCategoryId(1L);
        List<ItemVo> itemVoList =  itemQueryRepository.getItemList(itemListParam,itemListParam.of());
        System.out.println(itemVoList);
    }
}