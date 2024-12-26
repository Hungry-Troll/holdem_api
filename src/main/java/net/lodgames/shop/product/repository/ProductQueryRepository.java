package net.lodgames.shop.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.shop.product.param.ProductListParam;
import net.lodgames.shop.product.vo.ProductVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.shop.product.model.QProduct.product;


@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<ProductVo> getProductList(ProductListParam productListParam, PageRequest of) {
        return jpaQueryFactory.select(Projections.fields(ProductVo.class,
                        product.id,
                        product.name,
                        product.thumbnail,
                        product.image,
                        product.type,
                        product.stockQuantity,
                        product.info,
                        product.originPrice,
                        product.price,
                        product.description,
                        product.status,
                        product.createdAt,
                        product.updatedAt
                ))
                .from(product)
                .where(product.status.eq(productListParam.getStatus())) // 판매중인 것만
                .offset(of.getOffset())
                .limit(of.getPageSize())
                .orderBy(product.id.desc())
                .fetch();
    }
}
