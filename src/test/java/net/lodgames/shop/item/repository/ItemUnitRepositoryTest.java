package net.lodgames.shop.item.repository;

import jakarta.transaction.Transactional;
import net.lodgames.shop.item.constants.ItemUnitType;
import net.lodgames.shop.item.model.ItemUnit;
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
public class ItemUnitRepositoryTest {
    @Autowired
    private ItemUnitRepository itemUnitRepository;

    @Test
    @Rollback(false)
    void save_item_unit_then_item_unit_saved() {
        ItemUnit itemUnit = itemUnitRepository.save(ItemUnit.builder()
                .sku("sku000001")                  // 아이템 SKU
                .name("슈퍼 재화 아이템")            // 아이템 이름
                .image("/image/image1.png")        // 이미지 경로
                .description("완전 좋은 재화 아이템") // 설명
                .attributes("super super nice")    // 속성
                .type(ItemUnitType.CONSUMABLE)     // 상태
                .build());
        ItemUnit itemUni2 = itemUnitRepository.save(ItemUnit.builder()
                .sku("sku000002")                   // 아이템 SKU
                .name("슈퍼 성능 아이템")             // 아이템 이름
                .image("/image/image2.png")         // 이미지 경로
                .description("완전 좋은  성능 아이템") // 설명
                .attributes("very very nice")       // 속성
                .type(ItemUnitType.CONSUMABLE)      // 상태
                .build());
    }

}
