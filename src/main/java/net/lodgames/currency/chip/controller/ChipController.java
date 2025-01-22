package net.lodgames.currency.chip.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.chip.param.ChipCheatParam;
import net.lodgames.currency.chip.service.ChipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChipController {

    private final ChipService chipService;

    @GetMapping("/chip")
    public ResponseEntity<?> getChip(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(chipService.getChip(userPrincipal.getUserId()));
    }

    // TODO 금액 테스트용입니다. 운영에서 쓰이지 않도록 주의 바랍니다.
    @PutMapping("/chip/cheat")
    public ResponseEntity<?> chipCheat(@RequestBody ChipCheatParam chipCheatParam,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        chipCheatParam.setUserId(userId);
        chipService.chipCheat(chipCheatParam);
        return ResponseEntity.ok().build();
    }

}
