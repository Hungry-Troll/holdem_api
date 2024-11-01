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
                .count(100)
                .price(100)
                .originPrice(100)
                .build());

        ProductOption option = ProductOption.builder()
                .productId(product.getId())
                .type(ProductOptionType.COIN)
                .name("test Option")
                .quantity(100)
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
                .count(100)
                .price(100)
                .originPrice(100)
                .build());

        ProductOption option = productOptionRepository.save(ProductOption.builder().productId(product.getId())
                .type(ProductOptionType.COIN).name("testOption").quantity(100).build());


    }

    @Test
    @Rollback(false)
    void deleteAllByProductId(){
        //given
        Product product = Product.builder()
                .name("test")
                .description("test description")
                .status(ProductStatus.READY)
                .thumbnail("")
                .image("")
                .info("")
                .type(ProductType.CURRENCY)
                .count(100)
                .price(100)
                .originPrice(100)
                .build();

        productRepository.save(product);

        ProductOption option1 = productOptionRepository.save(ProductOption.builder().productId(product.getId()).type(ProductOptionType.COIN)
                .name("testOption1").quantity(100).build());
        ProductOption option2 = productOptionRepository.save(ProductOption.builder().productId(product.getId()).type(ProductOptionType.COIN)
                .name("testOption2").quantity(200).build());
        ProductOption option3 = productOptionRepository.save(ProductOption.builder().productId(product.getId()).type(ProductOptionType.COIN)
                .name("testOption3").quantity(300).build());
        ProductOption option4 = productOptionRepository.save(ProductOption.builder().productId(product.getId()).type(ProductOptionType.COIN)
                .name("testOption4").quantity(400).build());

    }
}
