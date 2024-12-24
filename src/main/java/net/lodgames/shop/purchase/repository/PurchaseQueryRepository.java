package net.lodgames.shop.purchase.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.shop.bundle.model.QBundle;
import net.lodgames.shop.bundle.vo.BundleVo;
import net.lodgames.shop.item.model.QItem;
import net.lodgames.shop.item.vo.ItemVo;
import net.lodgames.shop.purchase.param.PurchaseListParam;
import net.lodgames.shop.purchase.vo.PurchaseVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.shop.bundle.model.QBundle.bundle;
import static net.lodgames.shop.item.model.QItem.item;
import static net.lodgames.shop.purchase.model.QPurchase.purchase;


@Repository
@RequiredArgsConstructor
public class PurchaseQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<PurchaseVo> getPurchaseList(PurchaseListParam purchaseListParam, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(PurchaseVo.class,
                        purchase.id,    // 고우번호
                        purchase.userId,       // 유저 고유번호
                        purchase.paidAmount,   // 구매금액
                        purchase.purchaseType, // 구매타입
                        purchase.currencyType, // 재화타입
                        purchase.createdAt,    // 만든날짜
                        getItemVoExpression(item),
                        getBundleVoExpression(bundle)
                ))
                .from(purchase)
                .leftJoin(item).on(purchase.itemId.eq(item.id))
                .leftJoin(bundle).on(purchase.bundleId.eq(bundle.id))
                .where(
                        purchase.userId.eq(purchaseListParam.getUserId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(purchase.id.desc())
                .fetch();
    }

    public PurchaseVo getPurchase(Long purchaseId) {
        return jpaQueryFactory
                .select(Projections.fields(PurchaseVo.class,
                        purchase.id,           // 고우번호
                        purchase.userId,       // 유저 고유번호
                        purchase.paidAmount,   // 구매금액
                        purchase.purchaseType, // 구매타입
                        purchase.currencyType, // 재화타입
                        purchase.createdAt,    // 만든날짜
                        getItemVoExpression(item),
                        getBundleVoExpression(bundle)
                ))
                .from(purchase)
                .leftJoin(item).on(purchase.itemId.eq(item.id))
                .leftJoin(bundle).on(purchase.bundleId.eq(bundle.id))
                .where(
                        purchase.id.eq(purchaseId)
                )
                .fetchOne();
    }

    private Expression<ItemVo> getItemVoExpression(QItem item) {
        return Projections.fields(ItemVo.class,
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
                item.amount,        // 판매가격
                item.createdAt,     // 만든날짜
                item.updatedAt      // 변경일
        ).as("item");
    }

    private Expression<BundleVo> getBundleVoExpression(QBundle bundle) {
        return Projections.fields(BundleVo.class,
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
        ).as("bundle");
    }

}
