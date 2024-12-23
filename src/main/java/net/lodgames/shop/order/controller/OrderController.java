package net.lodgames.shop.order.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.shop.order.param.CancelOrderParam;
import net.lodgames.shop.order.param.OrderAcceptParam;
import net.lodgames.shop.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderService orderService;

    // 주문 접수 : 사전 주문 준비
    @PostMapping("/orders/accept")
    public ResponseEntity<?> acceptOrder(@RequestBody OrderAcceptParam orderAcceptParam,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        orderAcceptParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(orderService.acceptOrder(orderAcceptParam));
    }

    // 주문 취소 : 주문 생성 취소
    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable(name="orderId") Long orderId,
            @RequestBody CancelOrderParam cancelOrderParam,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        cancelOrderParam.setUserId(userPrincipal.getUserId());
        cancelOrderParam.setOrderId(orderId);
        return ResponseEntity.ok(orderService.cancelOrder(cancelOrderParam));
    }

}
