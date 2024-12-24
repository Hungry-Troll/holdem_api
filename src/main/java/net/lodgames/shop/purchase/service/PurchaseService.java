package net.lodgames.shop.purchase.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.currency.constants.CurrencyType;
import net.lodgames.currency.service.CoinService;
import net.lodgames.shop.bundle.constants.BundleStatus;
import net.lodgames.shop.bundle.model.Bundle;
import net.lodgames.shop.bundle.model.BundleCurrency;
import net.lodgames.shop.bundle.model.BundleItem;
import net.lodgames.shop.bundle.repository.BundleCurrencyRepository;
import net.lodgames.shop.bundle.repository.BundleItemRepository;
import net.lodgames.shop.bundle.repository.BundleRepository;
import net.lodgames.shop.collection.service.CollectionService;
import net.lodgames.shop.item.constants.ItemStatus;
import net.lodgames.shop.item.model.Item;
import net.lodgames.shop.item.repository.ItemRepository;
import net.lodgames.shop.purchase.constants.PurchaseType;
import net.lodgames.shop.purchase.model.Purchase;
import net.lodgames.shop.purchase.param.PurchaseListParam;
import net.lodgames.shop.purchase.param.PurchaseParam;
import net.lodgames.shop.purchase.repository.PurchaseQueryRepository;
import net.lodgames.shop.purchase.repository.PurchaseRepository;
import net.lodgames.shop.purchase.util.PurchaseMapper;
import net.lodgames.shop.purchase.vo.PurchaseVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PurchaseService {
    private final PurchaseQueryRepository purchaseQueryRepository;
    private final PurchaseRepository purchaseRepository;
    private final ItemRepository itemRepository;
    private final BundleRepository bundleRepository;
    private final PurchaseMapper purchaseMapper;
    private final CoinService coinService;
    private final CollectionService collectionService;
    private final BundleItemRepository bundleItemRepository;
    private final BundleCurrencyRepository bundleCurrencyRepository;

    // 구매 리스트
    @Transactional(readOnly = true)
    public List<PurchaseVo> getPurchaseList(PurchaseListParam purchaseListParam) {
        return purchaseQueryRepository.getPurchaseList(purchaseListParam, purchaseListParam.of());
    }

    // 구매 조회
    @Transactional(readOnly = true)
    public PurchaseVo getPurchase(Long purchaseId) {
        // 구매
        PurchaseVo purchaseVo = purchaseQueryRepository.getPurchase(purchaseId);
        if (ObjectUtils.isEmpty(purchaseVo)) {
            throw new RestException(ErrorCode.NOT_FOUND_PURCHASED_ITEM);
        }
        return purchaseVo;
    }

    // 구매 처리
    @Transactional(rollbackFor = Exception.class)
    public PurchaseVo doPurchase(PurchaseParam purchaseParam) {
        // 유저 고유번호
        Long userId = purchaseParam.getUserId();
        // 아이팀 번들 둘다 지정이 되었거나 둘다 지정이 안되었을 경우
        if (purchaseParam.getItemId() != null && purchaseParam.getBundleId() != null ||
                purchaseParam.getItemId() == null && purchaseParam.getBundleId() == null
        ) {
            throw new RestException(ErrorCode.ONE_BUNDLE_OR_ONE_ITEM_MUST_BE_SPECIFIED);
        }

        // 아이템을 지정
        if (purchaseParam.getItemId() != null) {
            Purchase purchase = doPurchaseItem(userId, purchaseParam.getItemId());
            return purchaseMapper.updatePurchaseToVo(purchase);
            // 아이템 지정 안함 ( 번들을 지정 )
        } else {
            Purchase purchase = doPurchaseBundle(userId, purchaseParam.getBundleId());
            return purchaseMapper.updatePurchaseToVo(purchase);
        }
    }

    // 번들을 구매 함 
    private Purchase doPurchaseBundle(Long userId, Long bundleId) {
        // 번들 구매 처리
        Bundle bundle = purchaseBundle(bundleId, userId);
        // 구매 내역
        Purchase purchase = purchaseRepository.save(
                Purchase.builder()
                        .purchaseType(getPurchaseType(bundle.getCurrencyType()))     // 구매함
                        .userId(userId)                         // 유저 고유번호
                        .bundleId(bundleId)                     // 번들
                        .paidAmount(bundle.getAmount())         // 번들 가격
                        .currencyType(bundle.getCurrencyType()) // 아이템 재화타입
                        .build()
        );

        // 번들 아이템 처리
        List<BundleItem> bundleItems = bundleItemRepository.findAllByBundleId(bundleId);
        for (BundleItem bundleItem : bundleItems) {
            collectionService.addCollection(bundleItem.getItem(), purchase.getId(), userId);
        }
        return purchase;
    }

    // 구매 아이템 
    private Purchase doPurchaseItem(Long userId, Long itemId) {
        Item item = purchaseItem(itemId, userId);
        Purchase purchase = purchaseRepository.save(
                Purchase.builder()
                        .purchaseType(getPurchaseType(item.getCurrencyType())) // 구매함
                        .userId(userId)                       // 유저 고유번호
                        .itemId(itemId)                           // 아이템
                        .paidAmount(item.getAmount())         // 아이템 가격
                        .currencyType(item.getCurrencyType()) // 아이템 재화타입;
                        .build()
        );
        // 아이템 재고 처리
        collectionService.addCollection(item, purchase.getId(), userId);
        return purchase;
    }

    // 재화 타입에 따른 구매 타입
    private PurchaseType getPurchaseType(CurrencyType currencyType) {
        return switch (currencyType) {
            case DIAMOND, COIN -> PurchaseType.BUY; // 재화(상품 구매) -> 구매 보유
            case EVENT -> PurchaseType.EVENT;       // 이벤트 -> 이벤트 보유
            case FREE -> PurchaseType.FREE;         // 무료 -> 무료 보유
            case CHIP -> null;                      // note : chip 은 물건 구매가 불가능하고 게임만 진행됨
        };
    }


    // 아이템 구매처리
    private Item purchaseItem(Long itemId, Long userId) {
        // 아이템
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_ITEM));
        // check item availability
        checkItemAvailability(item);
        // 아이템 금액 지불
        paidForShopping(item.getCurrencyType(), item.getAmount(), userId);
        return item;
    }

    // 번들 구매처리
    private Bundle purchaseBundle(Long bundleId, Long userId) {
        // 번들 찾기
        Bundle bundle = bundleRepository.findById(bundleId)
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_BUNDLE));
        // check bundle availability
        checkBundleAvailability(bundle);
        // 번들 금액 지불
        paidForShopping(bundle.getCurrencyType(), bundle.getAmount(), userId);
        // 번들 재화 처리
        List<BundleCurrency> bundleCurrencies = bundleCurrencyRepository.findAllByBundleId(bundleId);
        for (BundleCurrency bundleCurrency : bundleCurrencies) {
            // 구매 번들 재화 타입
            CurrencyType currencyType = bundleCurrency.getCurrencyType();
            // 구매 번들 재화 개수
            Long count = bundleCurrency.getCount();
            // 코인 지급
            if (Objects.requireNonNull(currencyType) == CurrencyType.COIN) {
                coinService.addCoinByBundleTransaction(userId, count);
            }
        }
        return bundle;
    }

    // 아이템 구매 가능 여부 체크
    private void checkItemAvailability(Item item) {
        // 판매중인 상품이 아님
        if (ItemStatus.ON_SALE != item.getStatus()) {
            throw new RestException(ErrorCode.ITEM_IS_NOT_ON_SALE);
        }
        // 한정 갯수 상품 갯수 체크
        Integer stockQuantity = item.getStockQuantity();
        // NOTE : stockQuantity 가 null 이면
        if (stockQuantity != null && stockQuantity < 1) {
            throw new RestException(ErrorCode.ITEM_NOT_ENOUGH_STOCK);
        }
    }

    // 번들 구매 가능 여부 체크
    private void checkBundleAvailability(Bundle bundle) {
        // 판매중인 상품이 아님
        if (BundleStatus.ON_SALE != bundle.getStatus()) {
            throw new RestException(ErrorCode.BUNDLE_IS_NOT_ON_SALE);
        }

        // 한정 갯수 상품 갯수 체크
        Integer stockQuantity = bundle.getStockQuantity();
        if (stockQuantity != null && stockQuantity < 1) {
            throw new RestException(ErrorCode.BUNDLE_NOT_ENOUGH_STOCK);
        }

        // 판매기간 체크
        if (!LocalDateTime.now().isAfter(bundle.getSaleStartDate())
                || !LocalDateTime.now().isBefore(bundle.getSaleEndDate())) {
            throw new RestException(ErrorCode.BUNDLE_IS_NOT_ON_SALE_PERIOD);
        }
    }

    // 재화 지불
    private void paidForShopping(CurrencyType currencyType, int amount, Long userId) {
        switch (currencyType) {
            case COIN -> {
                String idempotentKey = UUID.randomUUID().toString();
                coinService.useCoinForShopping(userId, amount, idempotentKey);
            }
            case DIAMOND -> {
                // error
            }
            case null, default -> { // FREE, EVENT
                // nothing TO DO
            }
        }
    }

    // 보관함에서 번들 받기 처리 
    public Long purchaseBundleByReceiveStorage(Long userId, Long bundleId) {
        Purchase purchase = doPurchaseBundle(userId, bundleId);
        return purchase.getId();
    }

    // 보관함에서 아이템 받기 처리
    public Long purchaseItemByReceiveStorage(Long userId, Long itemId) {
        Purchase purchase = doPurchaseItem(userId, itemId);
        return purchase.getId();
    }


}
