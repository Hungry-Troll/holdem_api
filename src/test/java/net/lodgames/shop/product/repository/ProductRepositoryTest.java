package net.lodgames.shop.product.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.shop.product.constants.ProductStatus;
import net.lodgames.shop.product.constants.ProductType;
import net.lodgames.shop.product.model.Product;
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

@Slf4j
@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@Import(QueryDsLTestConfig.class)
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Rollback(false)
    void saveProduct() {

        Product product =productRepository.save(Product.builder()
                .name("test production")
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

        Assertions.assertEquals("test production", product.getName());
        Assertions.assertEquals("test description", product.getDescription());
    }
}
