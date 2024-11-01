package net.lodgames.shop.order.state;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.deposit.service.DepositRecordService;
import net.lodgames.shop.order.constants.OrderStatus;
import net.lodgames.shop.order.model.Orders;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PaidState implements OrderState<Orders> {

    private final DepositRecordService depositRecordService;

    @Override
    public void paid(Orders orders) {
        throw new RestException(ErrorCode.FAIL_PAY_ORDER_ALREADY_PAID);
    }

    @Override
    public void cancel(Orders orders) {
        throw new RestException(ErrorCode.FAIL_CANCEL_ORDER_PAID);
    }

    @Override
    public void refund(Orders orders) {
        // 환불로 상태 변경
        orders.setOrderStatus(OrderStatus.REFUND);
        // 지불 기록 , 지불 재화기록 삭제 ( TODO 기획상 지울것인지 확인)
        depositRecordService.removeDepositRecordAndDepositCurrencyRecord(orders.getId());
    }
}

