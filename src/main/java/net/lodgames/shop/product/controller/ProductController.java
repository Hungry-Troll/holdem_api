package net.lodgames.shop.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.shop.product.param.ProductListParam;
import net.lodgames.shop.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    // 상품 목록 조회
    @GetMapping("/products")
    public ResponseEntity<?> getProductList(@RequestBody ProductListParam productListParam){
        return ResponseEntity.ok(productService.getProductList(productListParam));
    }

    // 상품 상세 조회
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") Long productId,  @AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(productService.getProduct(productId, userId));
    }

}
