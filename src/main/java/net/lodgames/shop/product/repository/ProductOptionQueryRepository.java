package net.lodgames.shop.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.shop.product.vo.ProductOptionVo;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.shop.product.model.QProductOption.productOption;

@Repository
@RequiredArgsConstructor
public class ProductOptionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //요청 상품 옵션 리스트 조회
    public List<ProductOptionVo> getProductOptionList(long productId) {
        return jpaQueryFactory.select(Projections.fields(ProductOptionVo.class,
                productOption.id,
                productOption.productId,
                productOption.name,
                productOption.type,
                productOption.quantity,
                productOption.createdAt))
                .from(productOption)
                .where(productOption.productId.eq(productId))
                .orderBy(productOption.id.desc())
                .fetch();
    }
}
