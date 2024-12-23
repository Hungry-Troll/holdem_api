package net.lodgames.shop.item.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.shop.item.constants.ItemUnitSearchType;
import net.lodgames.shop.item.param.ItemUnitListParam;
import net.lodgames.shop.item.vo.ItemUnitVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.shop.item.model.QItemUnit.itemUnit;
import static org.flywaydb.core.internal.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ItemUnitQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ItemUnitVo> getItemUnitList(ItemUnitListParam itemUnitListParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(ItemUnitVo.class,
                        itemUnit.id,          // 아이템 유닛 고유번호
                        itemUnit.name,        // 아이템 이름
                        itemUnit.image,       // 이미지 경로
                        itemUnit.sku,         // sku
                        itemUnit.description, // 설명
                        itemUnit.type,        // 아이템 유닛 타입
                        itemUnit.attributes,  // 아이템 유닛 속성
                        itemUnit.createdAt,   // 만든날짜
                        itemUnit.updatedAt    // 변경일
                ))
                .from(itemUnit)
                // 보유 확인
                .where(
                        keywordMatch(itemUnitListParam.getSearchType(),
                                itemUnitListParam.getSearchValue())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(itemUnit.id.desc())
                .fetch();
    }

    private BooleanExpression keywordMatch(ItemUnitSearchType searchType, String value) {
        if (searchType == null || !hasText(value)) {
            return null;
        }
        return switch (searchType) {
            case NAME -> itemUnit.name.contains(value);
            case SKU -> itemUnit.sku.contains(value);
        };
    }

}
