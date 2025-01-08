package net.lodgames.currency.common.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.common.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CurrencyController {
    private final CurrencyService currencyService;

    // 재화 모든 정보를 가져온다.
    @GetMapping("/currency")
    public ResponseEntity<?> getCurrencies(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(currencyService.getCurrencies(userPrincipal.getUserId()));
    }

}
