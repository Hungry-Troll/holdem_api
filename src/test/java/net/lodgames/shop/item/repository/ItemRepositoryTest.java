package net.lodgames.shop.item.repository;

import jakarta.transaction.Transactional;
import net.lodgames.currency.constants.CurrencyType;
import net.lodgames.shop.category.model.Category;
import net.lodgames.shop.category.repository.CategoryRepository;
import net.lodgames.shop.item.constants.ItemPeriodType;
import net.lodgames.shop.item.constants.ItemStatus;
import net.lodgames.shop.item.model.Item;
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
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Rollback(false)
    void save_item_then_item_saved() {
        Category category = Category.builder()
                .name("코인 재화")
                .description("인게임 재화")
                .build();
        categoryRepository.save(category);

        Item item = itemRepository.save(Item.builder()
                .categoryId(category.getId())      // 카테고리
                .itemUnitId(1L)                  // 아이템 유닛 고유번호
                .sku("skuu1000001")                  // 아이템 SKU
                .unitSku("skuu000001")              // 아이템 유닛 SKU
                .name("슈퍼 재화 아이템")            // 아이템 이름
                .description("완전 좋은 재화 아이템") // 설명
                .num(5)                            // 아이템 갯수
                .stockQuantity(null)               // 재고갯수
                .status(ItemStatus.READY)           // 상태
                .thumbnail("/thumbnail/thumbnail.png") // 썸네일
                .image("image/image.png")           // 이미지
                .info("코인이 20000개임")             // 정보
                .periodType(ItemPeriodType.NONE)    // 기간타입
                .period(null)                       // 기간
                .expiration(null)                   // 만료일
                .currencyType(CurrencyType.DIAMOND) // 재화타입
                .amount(3000)                       // 재화가격
                .build());
        itemRepository.save(item);

    }

}
