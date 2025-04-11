package net.lodgames.payment.google.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.payment.google.param.GooglePaymentFindNicknameParam;
import net.lodgames.payment.google.param.GooglePaymentFindOrderIdParam;
import net.lodgames.payment.google.param.GooglePaymentReceiptParam;
import net.lodgames.payment.google.service.GooglePaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class GooglePaymentController {

    private final GooglePaymentService googlePaymentService;

    // 구글 결제 영수증 검증
    @PostMapping("/google/verify-receipt")
    public ResponseEntity<?> verifyReceipt(@RequestBody GooglePaymentReceiptParam googlePaymentReceiptParam,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) throws IOException {
        return ResponseEntity.ok(googlePaymentService.consumePurchase(googlePaymentReceiptParam.getReceipt(),
                                                                      userPrincipal.getUserId()));
    }

    // 유저 닉네임으로 주문 기록 찾기
    @GetMapping("/google/payment/nickname")
    public ResponseEntity<?> getPaymentByNickname (@RequestBody GooglePaymentFindNicknameParam googlePaymentFindNicknameParam) {
        return ResponseEntity.ok(googlePaymentService.getPaymentByNickname(googlePaymentFindNicknameParam.getNickname()));
    }

    // 유저 아이디로 주문 기록 찾기
    @GetMapping("/google/payment/user-id")
    public ResponseEntity<?> getPaymentByUserId (@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(googlePaymentService.getPaymentByUserId(userPrincipal.getUserId()));
    }

    // 주문 기록 찾기
    @GetMapping("/google/payment/order-id")
    public ResponseEntity<?> getPaymentByOrderId (@RequestBody GooglePaymentFindOrderIdParam googlePaymentFindOrderIdParam) {
        return ResponseEntity.ok(googlePaymentService.getPaymentByOrderId(googlePaymentFindOrderIdParam.getOrderId()));
    }
}
