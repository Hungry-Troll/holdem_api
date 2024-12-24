package net.lodgames.shop.product.repository;

import jakarta.transaction.Transactional;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.shop.product.constants.ProductOptionType;
import net.lodgames.shop.product.constants.ProductStatus;
import net.lodgames.shop.product.constants.ProductType;
import net.lodgames.shop.product.model.Product;
import net.lodgames.shop.product.model.ProductOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@Import(QueryDsLTestConfig.class)
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductOptionRepositoryTest {

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Rollback(false)
    void saveProductOption() {

        //give
        Product product = productRepository.save(Product.builder()
                .name("test option")
                .description("test description")
                .status(ProductStatus.ON_SALE)
                .thumbnail("test thumbnail")
                .image("test image")
                .info("test info")
                .type(ProductType.CURRENCY)
                .stockQuantity(100)
                .price(100)
                .originPrice(100)
                .build());

        ProductOption option = ProductOption.builder()
                .productId(product.getId())
                .type(ProductOptionType.COIN)
                .name("test Option")
                .quantity(100L)
                .build();

        //when

        productOptionRepository.save(option);

        //then
        Assertions.assertEquals("test Option", option.getName());
    }

    @Test
    @Rollback(true)
    void existsByProductId(){
        //give
        Product product = productRepository.save(Product.builder()
                .name("test option")
                .description("test description")
                .status(ProductStatus.ON_SALE)
                .thumbnail("test thumbnail")
                .image("test image")
                .info("test info")
                .type(ProductType.CURRENCY)
                .stockQuantity(100)
                .price(100)
                .originPrice(100)
                .build());

        ProductOption option = productOptionRepository.save(ProductOption.builder().productId(product.getId())
                .type(ProductOptionType.COIN).name("testOption").quantity(100L).build());


    }

    @Test
    @Rollback(false)
    void deleteAllByProductId(){
        //given
        Product product = Product.builder()
                .name("유료상품1")
                .description("매우 좋은 유료 상품 입니다.")
                .status(ProductStatus.ON_SALE)
                .thumbnail("/image/thumbnail1.jpg")
                .image("/image/image1.jpg")
                .info("상품 구성 - 1")
                .type(ProductType.CURRENCY)
                .stockQuantity(100)
                .price(100)
                .originPrice(100)
                .build();
        productRepository.save(product);

        ProductOption option1 = productOptionRepository
                .save(ProductOption
                        .builder()
                        .productId(product.getId())
                        .type(ProductOptionType.COIN)
                .name("코인100").quantity(100L).build());
        ProductOption option2 = productOptionRepository
                .save(ProductOption
                        .builder()
                        .productId(product.getId())
                        .type(ProductOptionType.DIAMOND)
                .name("다이아몬드200").quantity(200L).build());
    }
}
