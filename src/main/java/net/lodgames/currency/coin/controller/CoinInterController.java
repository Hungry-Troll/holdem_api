package net.lodgames.currency.coin.controller;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/user/{userId}/coin")
    public ResponseEntity<?> getCoin(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(coinService.getCoin(userId));
    }

    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/user/{userId}/coin/cheat/{amount}")
    public ResponseEntity<?> coinCheat(@PathVariable("userId") Long userId, @PathVariable("amount") Long amount){
        coinService.coinCheat(userId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{userId}/coin/deposit")
    public ResponseEntity<?> coinDeposit(@PathVariable("userId") Long userId, @RequestBody CoinDepositParam coinDepositParam){
        coinDepositParam.setUserId(userId);
        return ResponseEntity.ok(coinService.coinDeposit(coinDepositParam));
    }

    @PostMapping("/user/{userId}/coin/withdraw")
    public ResponseEntity<?> coinWithdraw(@PathVariable("userId") Long userId, @RequestBody CoinWithdrawParam coinWithdrawParam ){
        coinWithdrawParam.setUserId(userId);
        return ResponseEntity.ok(coinService.coinWithdraw(coinWithdrawParam));
    }
}
