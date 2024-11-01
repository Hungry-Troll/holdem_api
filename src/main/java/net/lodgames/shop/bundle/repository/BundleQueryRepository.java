package net.lodgames.shop.bundle.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.shop.bundle.constants.BundleSearchType;
import net.lodgames.shop.bundle.param.BundleListParam;
import net.lodgames.shop.bundle.vo.BundleVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static net.lodgames.shop.bundle.model.QBundle.bundle;
import static org.flywaydb.core.internal.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class BundleQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<BundleVo> getBundleList(BundleListParam bundleListParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(BundleVo.class,
                        bundle.id,             // 번들 고유번호
                        bundle.name,           // 이름
                        bundle.sku,            // SKU
                        bundle.description,    // 설명
                        bundle.status,         // 상태
                        bundle.thumbnail,      // 썸네일
                        bundle.image,          // 이미지
                        bundle.info,           // 정보
                        bundle.countPerPerson, // 한명당 구매 가능 개수
                        bundle.saleStartDate,  // 판매 시작일
                        bundle.saleEndDate,    // 판매 종료일
                        bundle.currencyType,   // 재화타입
                        bundle.amount,         // 판매가격
                        bundle.originAmount,   // 원판매가격
                        bundle.stockQuantity,  // 재고갯수
                        bundle.createdAt,      // 만든날짜
                        bundle.updatedAt       // 변경일
                ))
                .from(bundle)
                .where(
                        keywordMatch(bundleListParam.getSearchType(), bundleListParam.getSearchValue()),
                        nowIsSaleStartDateAfterAndSaleEndDateBefore()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bundle.id.desc())
                .fetch();
    }

    private BooleanExpression nowIsSaleStartDateAfterAndSaleEndDateBefore() {
        return bundle.saleStartDate.before(LocalDateTime.now())
                .and(bundle.saleEndDate.after(LocalDateTime.now()));
    }


    private BooleanExpression keywordMatch(BundleSearchType searchType, String value) {
        if (searchType == null || !hasText(value)) {
            return null;
        }
        return switch (searchType) {
            case NAME -> bundle.name.contains(value);
            case SKU -> bundle.sku.contains(value);
        };
    }
}
