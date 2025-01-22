package net.lodgames.currency.diamond.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.diamond.param.DiamondCheatParam;
import net.lodgames.currency.diamond.service.DiamondService;
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
        return ResponseEntity.ok(diamondService.getDiamondVo(userPrincipal.getUserId()));
    }

    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/diamond/cheat")
    public ResponseEntity<?> diamondCheat(@RequestBody DiamondCheatParam diamondCheatParam ,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        diamondCheatParam.setUserId(userId);
        diamondService.diamondCheat(diamondCheatParam);
        return ResponseEntity.ok().build();
    }


}
