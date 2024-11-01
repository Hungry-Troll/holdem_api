package net.lodgames.shop.order.state;

import lombok.AllArgsConstructor;
import net.lodgames.shop.order.constants.OrderStatus;
import net.lodgames.shop.order.model.Orders;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderStatePackage {
    private final AcceptState acceptState;
    private final PaidState paidState;
    private final CancelState cancelState;
    private final RefundState refundState;

    public void changeStateByStatus(Orders orders, OrderStatus changeOrderStatus) {
        OrderState<Orders> orderState = getStateByStatus(orders.getOrderStatus());
        switch (changeOrderStatus) {
            case ACCEPT -> { /* not happen */}
            case PAID   -> orderState.paid(orders);
            case CANCEL -> orderState.cancel(orders);
            case REFUND -> orderState.refund(orders);
        }
    }

    private OrderState<Orders> getStateByStatus(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case ACCEPT -> acceptState;
            case PAID   -> paidState;
            case CANCEL -> cancelState;
            case REFUND -> refundState;
        };
    }
}
