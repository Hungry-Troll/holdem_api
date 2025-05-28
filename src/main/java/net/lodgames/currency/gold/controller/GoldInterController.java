package net.lodgames.currency.gold.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.currency.gold.param.GoldCheatParam;
import net.lodgames.currency.gold.param.GoldDepositParam;
import net.lodgames.currency.gold.param.GoldWithdrawParam;
import net.lodgames.currency.gold.service.GoldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inter/api/v1")
public class GoldInterController {

    private final GoldService goldService;

    @GetMapping("/users/{userId}/gold")
    public ResponseEntity<?> getGold(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(goldService.getGold(userId));
    }

    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/users/{userId}/gold/cheat")
    public ResponseEntity<?> goldCheat(@PathVariable("userId") Long userId, @RequestBody GoldCheatParam goldCheatParam){
        goldCheatParam.setUserId(userId);
        goldService.goldCheat(goldCheatParam);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/gold/deposit")
    public ResponseEntity<?> goldDeposit(@PathVariable("userId") Long userId, @RequestBody GoldDepositParam goldDepositParam){
        goldDepositParam.setUserId(userId);
        return ResponseEntity.ok(goldService.goldDeposit(goldDepositParam));
    }

    @PostMapping("/users/{userId}/gold/withdraw")
    public ResponseEntity<?> goldWithdraw(@PathVariable("userId") Long userId, @RequestBody GoldWithdrawParam goldWithdrawParam ){
        goldWithdrawParam.setUserId(userId);
        return ResponseEntity.ok(goldService.goldWithdraw(goldWithdrawParam));
    }
}
