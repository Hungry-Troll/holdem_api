package net.lodgames.shop.bundle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.shop.bundle.param.BundleListParam;
import net.lodgames.shop.bundle.param.BundleParam;
import net.lodgames.shop.bundle.service.BundleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BundleController {

    private final BundleService bundleService;

    // 번들 목록 조회
    @GetMapping("/bundles")
    public ResponseEntity<?> getBundleList(@RequestBody BundleListParam bundleListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        bundleListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(bundleService.getBundleList(bundleListParam));
    }

    // 번들 상세 조회
    @GetMapping("/bundles/{bundleId}")
    public ResponseEntity<?> getBundle(@PathVariable("bundleId") Long bundleId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(bundleService.getBundle(BundleParam.builder()
                .bundleId(bundleId)
                .userId(userPrincipal.getUserId())
                .build()));
    }

}
