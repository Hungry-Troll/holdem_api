package net.lodgames.shop.product.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.shop.product.constants.ProductStatus;
import net.lodgames.shop.product.constants.ProductType;
import net.lodgames.shop.product.model.Product;
import net.lodgames.shop.product.param.ProductListParam;
import net.lodgames.shop.product.vo.ProductVo;
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

import java.util.List;

@Slf4j
@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@Import(QueryDsLTestConfig.class)
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductQueryRepositoryTest {

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Test
    @Rollback(true)
    void getProductList(){
        //given
        productRepository.deleteAll();

        Product product1 = Product.builder()
                .name("test1")
                .description("test1 description")
                .status(ProductStatus.ON_SALE)
                .thumbnail("")
                .image("")
                .info("")
                .type(ProductType.CURRENCY)
                .count(100)
                .price(100)
                .originPrice(100)
                .build();

        Product product2 = Product.builder()
                .name("test2")
                .description("test2 description")
                .status(ProductStatus.ON_SALE)
                .thumbnail("")
                .image("")
                .info("")
                .type(ProductType.CURRENCY)
                .count(100)
                .price(100)
                .originPrice(100)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        ProductListParam param = new ProductListParam();
        param.setPage(1);
        param.setLimit(10);

        //when
        List<ProductVo> result = productQueryRepository.getProductList(param, param.of());

        //then
        Assertions.assertEquals(result.get(0).getName(), product2.getName());
        Assertions.assertEquals(result.get(1).getName(), product1.getName());
    }
}
