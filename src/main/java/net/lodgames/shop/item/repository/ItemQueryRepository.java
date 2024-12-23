package net.lodgames.shop.item.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.shop.item.constants.ItemSearchType;
import net.lodgames.shop.item.param.ItemListParam;
import net.lodgames.shop.item.vo.ItemVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.shop.collection.model.QCollection.collection;
import static net.lodgames.shop.item.model.QItem.item;
import static org.flywaydb.core.internal.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ItemVo> getItemList(ItemListParam itemListParam, Pageable pageable){
        return jpaQueryFactory
                .select(Projections.fields(ItemVo.class,
                        item.id,            // 아이템 고유번호
                        item.categoryId,    // 카테고리
                        item.itemUnitId,    // 아이템 유닛 고유번호
                        item.sku,           // 아이템 SKU
                        item.unitSku,       // 아이템 유닛 SKU
                        item.name,          // 아이템 이름
                        item.description,   // 설명
                        item.num,           // 아이템 갯수 
                        item.stockQuantity, // 재고갯수
                        item.status,        // 상태
                        item.thumbnail,     // 썸네일
                        item.image,         // 이미지
                        item.info,          // 정보
                        item.periodType,    // 기간타입
                        item.period,        // 기간
                        item.expiration,    // 만료일
                        item.currencyType,  // 재화타입
                        item.amount,        // 재화가격
                        item.createdAt,     // 만든날짜
                        item.updatedAt,     // 변경일
                        isCollection().as("isCollection")
                ))
                .from(item)
                // 보유 확인
                .leftJoin(collection).on(collection.itemId.eq(item.id)
                        .and(collection.userId.eq(itemListParam.getUserId())))
                .where(
                        eqCategory(itemListParam.getCategoryId()),
                        keywordMatch(itemListParam.getSearchType(), itemListParam.getSearchValue())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(item.id.desc())
                .fetch();
    }

    private BooleanExpression keywordMatch(ItemSearchType searchType, String value) {
        if (searchType == null || !hasText(value)) {
            return null;
        }
        return switch (searchType) {
            case NAME -> item.name.contains(value);
            case SKU -> item.sku.contains(value);
        };
    }

    private BooleanExpression eqCategory(Long categoryId) {
        return categoryId == null ? null : item.categoryId.eq(categoryId);
    }

    private BooleanExpression isCollection(){
        return new CaseBuilder().when(collection.id.isNull()).then(false).otherwise(true);
    }

}
