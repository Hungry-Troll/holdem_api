package net.lodgames.shop.purchase.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.shop.purchase.param.PurchaseListParam;
import net.lodgames.shop.purchase.param.PurchaseParam;
import net.lodgames.shop.purchase.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PurchaseController {

    private final PurchaseService purchaseService;

    // 이미 구매한 아이템 목록 조회
    @GetMapping("/purchases")
    public ResponseEntity<?> getPurchaseList(@RequestBody PurchaseListParam purchaseListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        purchaseListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(purchaseService.getPurchaseList(purchaseListParam));
    }

    // 구매 항목
    @GetMapping("/purchases/{purchaseId}")
    public ResponseEntity<?> getPurchase(@PathVariable("purchaseId") Long purchaseId) {
        return ResponseEntity.ok(purchaseService.getPurchase(purchaseId));
    }

    // 구매 처리
    @PostMapping("/purchases")
    public ResponseEntity<?> doPurchase(@RequestBody PurchaseParam purchaseParam , @AuthenticationPrincipal UserPrincipal userPrincipal) {
        purchaseParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(purchaseService.doPurchase(purchaseParam));
    }
}
