package net.lodgames.shop.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.currency.coin.service.CoinService;
import net.lodgames.currency.common.constants.CurrencyType;
import net.lodgames.currency.diamond.service.DiamondService;
import net.lodgames.shop.deposit.service.DepositRecordService;
import net.lodgames.shop.product.constants.ProductOptionType;
import net.lodgames.shop.product.model.ProductOption;
import net.lodgames.shop.product.repository.ProductOptionRepository;
import net.lodgames.user.constants.Os;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductOptionService {

    private final DiamondService diamondService;
    private final CoinService coinService;
    private final DepositRecordService depositRecordService;
    private final ProductOptionRepository productOptionRepository;
    private final String ORDER_IDEMPOTENT_KEY_HEADER = "ORDER:";
    private final String ALGORITHM_MD5 = "MD5";


    public void handleOrderProductOptions(Long userId, Os os, Long productId, Long depositRecordId, Long orderId) {
        // 상품 옵션 정보 취득
        List<ProductOption> productOptions = productOptionRepository.findAllByProductId(productId);
        // 복수의 상품 옵션 처리
        for (ProductOption productOption : productOptions) {
            // 상품 옵션 처리
            handleOrderProductOptions(productOption, userId, os, depositRecordId, orderId);
        }
    }

    // 주문한 상품 옵션을 처리 한다.
    public void handleOrderProductOptions(ProductOption productOption, Long userId, Os os, Long depositRecordId, Long orderId) {
        // 상품 옵션 타입
        ProductOptionType productOptionType = productOption.getType();
        // 주문 번호에 따른 멱등키
        String idempotentKey = makeOrderIdempotentKey(orderId);
        // 재화 처리일 경우 지불 재화 기록을 위한 재화 타입 초기화
        CurrencyType currencyType = null;
        switch (productOptionType) { // 상품 옵션 타입
            case DIAMOND -> { // 다이아몬드
                diamondService.addDiamondByOrder(userId, productOption.getQuantity(), idempotentKey, os);
                currencyType = CurrencyType.DIAMOND;
            }
            case COIN -> { // 코인
                coinService.addCoinByOrder(userId, productOption.getQuantity(), idempotentKey);
                currencyType = CurrencyType.COIN;
            }
            case ITEM -> { // 아이템
                // 아이템 처리
            }
            // other
        }
        if (currencyType != null) { // 지불 재화 기록 생성여부 결정을 위하여 재화 타입 체크
            // 지불 재화 기록 생성
            depositRecordService.addDepositCurrencyRecord(depositRecordId, currencyType, productOption.getQuantity(), idempotentKey);
        }
    }

    // 주문번호용 멱등키 생성
    protected String makeOrderIdempotentKey(Long orderId) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(ALGORITHM_MD5);
        } catch (NoSuchAlgorithmException e) {
            return ORDER_IDEMPOTENT_KEY_HEADER + UUID.randomUUID();
        }
        String orderIdStr = String.valueOf(orderId);
        md.update(orderIdStr.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return ORDER_IDEMPOTENT_KEY_HEADER + hash;
    }
}
