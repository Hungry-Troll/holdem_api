package net.lodgames.currency.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.service.DiamondService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DiamondController {

    private final DiamondService diamondService;

    @GetMapping("/diamond")
    public ResponseEntity<?> getDiamond(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(diamondService.getDiamond(userPrincipal.getUserId()));
    }

    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/diamond/cheat/{amount}")
    public ResponseEntity<?> diamondCheat(@PathVariable("amount") Long amount, @AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        diamondService.diamondCheat(userId, amount);
        return ResponseEntity.ok().build();
    }


}
