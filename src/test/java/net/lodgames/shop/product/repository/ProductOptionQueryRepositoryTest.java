package net.lodgames.shop.product.repository;

import jakarta.transaction.Transactional;
import net.lodgames.config.QueryDsLTestConfig;
import net.lodgames.shop.product.constants.ProductOptionType;
import net.lodgames.shop.product.constants.ProductStatus;
import net.lodgames.shop.product.constants.ProductType;
import net.lodgames.shop.product.model.Product;
import net.lodgames.shop.product.model.ProductOption;
import net.lodgames.shop.product.vo.ProductOptionVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@Import(QueryDsLTestConfig.class)
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductOptionQueryRepositoryTest {
    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductOptionQueryRepository productOptionQueryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getProductOption(){
        //given
        Product product = productRepository.save(Product.builder().name("test").description("test description")
                .status(ProductStatus.ON_SALE).thumbnail("").image("").info("").type(ProductType.CURRENCY)
                .count(100).price(100).originPrice(100).build());

        ProductOption option1 = ProductOption.builder().productId(product.getId()).type(ProductOptionType.COIN)
                .name("testOption1").quantity(100).build();
        ProductOption option2 = ProductOption.builder().productId(product.getId()).type(ProductOptionType.COIN)
                .name("testOption2").quantity(200).build();
        ProductOption option3 = ProductOption.builder().productId(product.getId()).type(ProductOptionType.COIN)
                .name("testOption3").quantity(300).build();
        ProductOption option4 = ProductOption.builder().productId(product.getId()).type(ProductOptionType.COIN)
                .name("testOption4").quantity(400).build();

        productOptionRepository.save(option1);
        productOptionRepository.save(option2);
        productOptionRepository.save(option3);
        productOptionRepository.save(option4);

        //when
        List< ProductOptionVo> list = productOptionQueryRepository.getProductOptionList(product.getId());

        //then
        Assertions.assertEquals(list.get(0).getName(), option4.getName());
        Assertions.assertEquals(list.get(1).getName(), option3.getName());
        Assertions.assertEquals(list.get(2).getName(), option2.getName());
        Assertions.assertEquals(list.get(3).getName(), option1.getName());
    }
}
