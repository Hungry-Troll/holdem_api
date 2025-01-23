package net.lodgames.currency.coin.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.currency.coin.param.CoinCheatParam;
import net.lodgames.currency.coin.param.CoinDepositParam;
import net.lodgames.currency.coin.param.CoinWithdrawParam;
import net.lodgames.currency.coin.service.CoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inter/api/v1")
public class CoinInterController {

    private final CoinService coinService;

    @GetMapping("/users/{userId}/coin")
    public ResponseEntity<?> getCoin(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(coinService.getCoin(userId));
    }

    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/users/{userId}/coin/cheat/{amount}")
    public ResponseEntity<?> coinCheat(@PathVariable("userId") Long userId, @RequestBody CoinCheatParam coinCheatParam){
        coinCheatParam.setUserId(userId);
        coinService.coinCheat(coinCheatParam);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/coin/deposit")
    public ResponseEntity<?> coinDeposit(@PathVariable("userId") Long userId, @RequestBody CoinDepositParam coinDepositParam){
        coinDepositParam.setUserId(userId);
        return ResponseEntity.ok(coinService.coinDeposit(coinDepositParam));
    }

    @PostMapping("/users/{userId}/coin/withdraw")
    public ResponseEntity<?> coinWithdraw(@PathVariable("userId") Long userId, @RequestBody CoinWithdrawParam coinWithdrawParam ){
        coinWithdrawParam.setUserId(userId);
        return ResponseEntity.ok(coinService.coinWithdraw(coinWithdrawParam));
    }
}
