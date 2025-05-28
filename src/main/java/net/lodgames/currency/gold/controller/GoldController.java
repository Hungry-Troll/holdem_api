package net.lodgames.currency.gold.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.gold.param.GoldCheatParam;
import net.lodgames.currency.gold.service.GoldService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class GoldController {

    private final GoldService goldService;

    @GetMapping("/gold")
    public ResponseEntity<?> getGold(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(goldService.getGold(userPrincipal.getUserId()));
    }

    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/gold/cheat")
    public ResponseEntity<?> goldCheat(@RequestBody GoldCheatParam goldCheatParam,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        goldCheatParam.setUserId(userId);
        goldService.goldCheat(goldCheatParam);
        return ResponseEntity.ok().build();
    }

}
