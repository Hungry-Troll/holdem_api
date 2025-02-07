package net.lodgames.stamina.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.stamina.param.StaminaAcquireParam;
import net.lodgames.stamina.param.StaminaConsumeParam;
import net.lodgames.stamina.param.StaminaModParam;
import net.lodgames.stamina.service.StaminaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class StaminaController {
    private final StaminaService staminaService;

    // 스태미나 생성
    @PostMapping("/stamina")
    public ResponseEntity<?> addStamina(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        staminaService.addStamina(userPrincipal.getUserId());
        return ResponseEntity.ok().build();
    }

    // 스태미나 조회
    @GetMapping("/stamina")
    public ResponseEntity<?> getStamina(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(staminaService.getStamina(userPrincipal.getUserId()));
    }

    // 내부 테스트 용도
    // 스태미나 변경
    @PutMapping("/stamina")
    public ResponseEntity<?> modStamina(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @RequestBody StaminaModParam staminaModParam) {
        staminaModParam.setUserId(userPrincipal.getUserId());
        staminaService.modStamina(staminaModParam);
        return ResponseEntity.ok().build();
    }

    // 내부 테스트 용도
    // 스태미나 삭제
    @DeleteMapping("/stamina")
    public ResponseEntity<?> deleteStamina(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        staminaService.deleteStamina(userPrincipal.getUserId());
        return ResponseEntity.ok().build();
    }

    // 타입별 스태미나 소모
    @PutMapping("/stamina/consume")
    public ResponseEntity<?> consumeStamina(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @RequestBody StaminaConsumeParam staminaConsumeParam) {
        staminaConsumeParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(staminaService.consumeStamina(staminaConsumeParam));
    }

    // 타입별 스태미나 습득
    @PutMapping("/stamina/acquire")
    public ResponseEntity<?> acquireStamina(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @RequestBody StaminaAcquireParam staminaAcquireParam) {
        staminaAcquireParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(staminaService.acquireStamina(staminaAcquireParam));
    }
}
