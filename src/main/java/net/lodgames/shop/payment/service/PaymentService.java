package net.lodgames.shop.payment.service;

import lombok.AllArgsConstructor;
import net.lodgames.shop.order.model.Orders;
import net.lodgames.shop.order.service.OrderService;
import net.lodgames.shop.payment.model.Payment;
import net.lodgames.shop.payment.param.PaymentParam;
import net.lodgames.shop.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Transactional(rollbackFor = Exception.class)
    public void payment(PaymentParam paymentParam) {
        Orders orders = orderService.retrieveOrder(paymentParam.getOrderId());
        // TODO verifyReceipt GOOGLE OR ISO
        verifyReceipt( orders.getId(), orders.getPaymentPrice(),  paymentParam.getPurchaseToken());
        Payment payment = Payment.builder()
                .amount(orders.getPaymentPrice())    // 결제 금액
                .method(paymentParam.getMethod())   // 결제 방법
                .orderId(paymentParam.getOrderId()) // 주문 고유번호
                .userId(paymentParam.getUserId())   // 유저 고유번호
                .build();
        paymentRepository.save(payment);
        orderService.payOrder(orders.getId());

    }

    private void verifyReceipt(Long orderId,Integer paymentPrice, String purchaseToken) {
        // verify to App stores
        // check payment status
        // check payment price
    }


}
