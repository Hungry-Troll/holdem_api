package net.lodgames.currency.diamond.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.currency.diamond.param.DiamondCheatParam;
import net.lodgames.currency.diamond.param.DiamondDepositParam;
import net.lodgames.currency.diamond.param.DiamondWithdrawParam;
import net.lodgames.currency.diamond.service.DiamondService;
import net.lodgames.user.constants.Os;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 다이아몬드 내부 호출 컨트롤러 : 유료 재화 - 돈으로 살 수 있음.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/inter/api/v1")
public class DiamondInterController {

    private final DiamondService diamondService;

    // 다이아몬드 정보 취득
    @GetMapping("/users/{userId}/diamond/{os}")
    public ResponseEntity<?> getDiamond(@PathVariable("userId") Long userId,@PathVariable("os") Os os) {
        return ResponseEntity.ok(diamondService.getDiamondVo(userId, os));
    }

    // 다이아몬드 치트
    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/users/{userId}/diamond/cheat")
    public ResponseEntity<?> diamondCheat(@PathVariable("userId") Long userId, @RequestBody DiamondCheatParam diamondCheatParam) {
        diamondCheatParam.setUserId(userId);
        diamondService.diamondCheat(diamondCheatParam);
        return ResponseEntity.ok().build();
    }

    // 다이아몬드 입금
    @PostMapping("/user/{userId}/diamond/deposit")
    public ResponseEntity<?> diamondDeposit(@PathVariable("userId") Long userId, @RequestBody DiamondDepositParam diamondDepositParam){
        diamondDepositParam.setUserId(userId);
        return ResponseEntity.ok(diamondService.diamondDeposit(diamondDepositParam));
    }

    // 다이아몬드 출금
    @PostMapping("/user/{userId}/diamond/withdraw")
    public ResponseEntity<?> diamondWithdraw(@PathVariable("userId") Long userId, @RequestBody DiamondWithdrawParam diamondWithdrawParam ){
        diamondWithdrawParam.setUserId(userId);
        return ResponseEntity.ok(diamondService.diamondWithdraw(diamondWithdrawParam));
    }

}
