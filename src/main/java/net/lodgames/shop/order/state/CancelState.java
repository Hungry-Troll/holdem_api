package net.lodgames.shop.order.state;

import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.order.model.Orders;
import org.springframework.stereotype.Component;

@Component
public class CancelState implements OrderState<Orders> {

    @Override
    public void paid(Orders orders) {
        throw new RestException(ErrorCode.FAIL_PAY_ORDER_CANCELED);
    }

    @Override
    public void cancel(Orders orders) {
        throw new RestException(ErrorCode.FAIL_CANCEL_ORDER_ALREADY_CANCEL);
    }

    @Override
    public void refund(Orders orders) {
        throw new RestException(ErrorCode.FAIL_REFUND_ORDER_NOT_PAID);
    }
}

