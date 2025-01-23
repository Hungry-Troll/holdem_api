package net.lodgames.currency.chip.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.currency.chip.param.ChipCheatParam;
import net.lodgames.currency.chip.param.ChipDepositParam;
import net.lodgames.currency.chip.param.ChipWithdrawParam;
import net.lodgames.currency.chip.service.ChipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inter/api/v1")
public class ChipInterController {

    private final ChipService chipService;

    @GetMapping("/users/{userId}/chip")
    public ResponseEntity<?> getChip(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(chipService.getChip(userId));
    }

    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/users/{userId}/chip/cheat")
    public ResponseEntity<?> chipCheat(@PathVariable("userId") Long userId, @RequestBody ChipCheatParam chipCheatParam){
        chipCheatParam.setUserId(userId);
        chipService.chipCheat(chipCheatParam);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/chip/deposit")
    public ResponseEntity<?> chipDeposit(@PathVariable("userId") Long userId, @RequestBody ChipDepositParam chipDepositParam){
        chipDepositParam.setUserId(userId);
        return ResponseEntity.ok(chipService.chipDeposit(chipDepositParam));
    }

    @PostMapping("/users/{userId}/chip/withdraw")
    public ResponseEntity<?> chipWithdraw(@PathVariable("userId") Long userId, @RequestBody ChipWithdrawParam chipWithdrawParam ){
        chipWithdrawParam.setUserId(userId);
        return ResponseEntity.ok(chipService.chipWithdraw(chipWithdrawParam));
    }
}
