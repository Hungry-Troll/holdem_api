package net.lodgames.shop.payment.controller;

import lombok.AllArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.shop.payment.param.PaymentParam;
import net.lodgames.shop.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;

    // 구매처리 요청
    @PostMapping("/payments")
    public ResponseEntity<?> payment(@RequestBody PaymentParam paymentParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        paymentParam.setUserId(userPrincipal.getUserId());
        paymentService.payment(paymentParam);
        return ResponseEntity.ok().build();
    }
}
