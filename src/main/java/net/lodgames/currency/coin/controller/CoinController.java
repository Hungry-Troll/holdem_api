package net.lodgames.currency.coin.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.coin.param.CoinCheatParam;
import net.lodgames.currency.coin.service.CoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CoinController {

    private final CoinService coinService;

    @GetMapping("/coin")
    public ResponseEntity<?> getCoin(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(coinService.getCoin(userPrincipal.getUserId()));
    }

    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/coin/cheat")
    public ResponseEntity<?> coinCheat(@RequestBody CoinCheatParam coinCheatParam,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        coinCheatParam.setUserId(userId);
        coinService.coinCheat(coinCheatParam);
        return ResponseEntity.ok().build();
    }

}
