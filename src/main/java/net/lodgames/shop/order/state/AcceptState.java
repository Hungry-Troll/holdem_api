package net.lodgames.shop.order.state;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.deposit.model.DepositRecord;
import net.lodgames.shop.deposit.service.DepositRecordService;
import net.lodgames.shop.order.constants.OrderStatus;
import net.lodgames.shop.order.model.Orders;
import net.lodgames.shop.product.service.ProductOptionService;
import net.lodgames.shop.product.service.ProductService;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AcceptState implements OrderState<Orders> {
    private final DepositRecordService depositRecordService;
    private final ProductService productService;
    private final ProductOptionService productOptionService;

    @Override
    public void paid(Orders orders) {
        // 유저 고유번호
        long userId = orders.getUserId();
        // 지불 상태로 변경
        orders.setOrderStatus(OrderStatus.PAID);
        // 상품 아이디
        Long productId = orders.getProductId();
        // 상품 가격
        Integer paymentPrice = productService.getProductPrice(productId);
        // check total purchase amount within this month (사전 주문에서 체크 했지만 한번 더 체크)
        depositRecordService.checkTotalPurchaseAmountWithinThisMonth(userId, paymentPrice);
        // 상품 갯수 처리
        productService.decreaseCountIfHasQuantityLimit(productId);
        // 지불 기록 생성
        DepositRecord depositRecord = depositRecordService.addDepositRecord(orders.getId(), productId, paymentPrice, userId);
        // 상품 옵션 구매 처리
        productOptionService.handleOrderProductOptions(userId, productId, depositRecord.getId(), orders.getId());
    }

    @Override
    public void cancel(Orders orders) {
        // 주문 단계에서만 취소 가능, 그외는 에러 발생
        orders.setOrderStatus(OrderStatus.CANCEL);
    }

    @Override
    public void refund(Orders orders) {
        // EXCEPTION
        throw new RestException(ErrorCode.FAIL_ACCEPT_ORDER_PAID_ORDER);
    }
}

