package net.lodgames.shop.bundle.repository;

import jakarta.transaction.Transactional;
import net.lodgames.currency.constants.CurrencyType;
import net.lodgames.shop.bundle.constants.BundleStatus;
import net.lodgames.shop.bundle.model.Bundle;
import net.lodgames.shop.bundle.model.BundleCurrency;
import net.lodgames.shop.bundle.model.BundleItem;
import net.lodgames.shop.category.model.Category;
import net.lodgames.shop.category.repository.CategoryRepository;
import net.lodgames.shop.item.constants.ItemPeriodType;
import net.lodgames.shop.item.constants.ItemStatus;
import net.lodgames.shop.item.constants.ItemUnitType;
import net.lodgames.shop.item.model.Item;
import net.lodgames.shop.item.model.ItemUnit;
import net.lodgames.shop.item.repository.ItemRepository;
import net.lodgames.shop.item.repository.ItemUnitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BundleRepositoryTest {

    @Autowired
    private ItemUnitRepository itemUnitRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BundleCurrencyRepository bundleCurrencyRepository;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private BundleItemRepository bundleItemRepository;

    @Test
    @Rollback(false)
    void save_bundle_then_bundle_saved() {
        Category category = Category.builder()
                .name("카드덱")
                .description("카드덱 카테고리")
                .build();
        categoryRepository.save(category);

        ItemUnit itemUnit = itemUnitRepository.save(ItemUnit.builder()
                .sku("sku000011")                    // 아이템 SKU
                .name("블루 카드덱")                   // 아이템 이름
                .image("/image/image_blue_card.png") // 이미지 경로
                .description("파란색 카드덱 ")         // 설명
                .attributes("card_deck_blue")        // 속성
                .type(ItemUnitType.EXPIRATION)       // 상태
                .build());

        Item item = itemRepository.save(Item.builder()
                .categoryId(category.getId())           // 카테고리
                .itemUnitId(itemUnit.getId())           // 아이템 유닛 고유번호
                .sku("sku000002")                       // 아이템 SKU
                .unitSku(itemUnit.getSku())             // 아이템 유닛 SKU
                .name("블루 카드덱")                      // 아이템 이름
                .description("블루 카드덱 입니다.")        // 설명
                .num(1)                                 // 아이템 갯수
                .stockQuantity(null)                    // 재고갯수
                .status(ItemStatus.READY)               // 상태
                .thumbnail("/thumbnail/blue_card.png")  // 썸네일
                .image("image/item_blue_card.png")      // 이미지
                .info("코인이 20000개임")                 // 정보
                .periodType(ItemPeriodType.MONTH)       // 기간타입
                .period(1)                              // 기간
                .expiration(null)                       // 만료일
                .currencyType(CurrencyType.DIAMOND)     // 재화타입
                .amount(3000)                           // 재화가격
                .build());
        itemRepository.save(item);

        Bundle bundle = bundleRepository.save(Bundle.builder()
                .name("카드덱 번들 아이템")               // 아이템 이름
                .sku("bundle000001")                   // 아이템 SKU
                .description("완전 좋은 재화 아이템")     // 설명
                .status(BundleStatus.READY)            // 상태
                .thumbnail("/thumbnail/thumbnail.png") // 썸네일
                .image("image/image.png")              // 이미지
                .info("코인이 20000개임")                // 정보
                .countPerPerson(0)                     // 한명당 구매 가능 개수
                .saleStartDate(LocalDateTime.now())    // 판매 시작일
                .saleEndDate(LocalDateTime.now().plusDays(30)) // 판매 종료일
                .stockQuantity(null)                   // 재고갯수
                .currencyType(CurrencyType.COIN)       // 재화타입 (구매)
                .amount(3000)                          // 재화가격
                .originAmount(3000)                    // 원판매가격
                .build());
        bundleRepository.save(bundle);

        BundleCurrency bundleCurrency = bundleCurrencyRepository.save(
                BundleCurrency.builder()
                        .bundleId(bundle.getId())
                        .count(10000)
                        .currencyType(CurrencyType.COIN)
                        .createdAt(LocalDateTime.now())
                        .build());

        BundleItem bundleItem = bundleItemRepository.save(
                BundleItem.builder()
                        .bundle(bundle)
                        .item(item)
                        .build()
        );

    }
}
