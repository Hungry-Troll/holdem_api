package net.lodgames.shop.collection.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.shop.collection.param.CollectionListParam;
import net.lodgames.shop.collection.vo.CollectionVo;
import net.lodgames.shop.item.vo.ItemVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.shop.collection.model.QCollection.collection;
import static net.lodgames.shop.item.model.QItem.item;


@Repository
@RequiredArgsConstructor
public class CollectionQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<CollectionVo> getCollectionList(CollectionListParam collectionListParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(CollectionVo.class,
                        collection.id,              // 수집 고유번호
                        collection.itemId,
                        collection.userId,          // 유저 고유번호
                        collection.purchaseId,      // 구매 아이디
                        collection.periodType,      // 기간 타입
                        collection.expireDatetime,  // 만료 기한
                        collection.createdAt,       // 생성일
                        collection.updatedAt,       // 변경일
                        Projections.fields(ItemVo.class,
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
                                item.createdAt,     // 만든날짜
                                item.updatedAt      // 변경일
                        ).as("item")
                ))
                .from(collection)
                .leftJoin(item).on(collection.itemId.eq(item.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(collection.createdAt.desc())
                .fetch();
    }
}
