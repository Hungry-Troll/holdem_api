package net.lodgames.ingame.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.ingame.param.InGameAuthenticationKeyParam;
import net.lodgames.ingame.service.InGameAuthenticationService;
import net.lodgames.ingame.vo.InGameAuthenticatedKeyVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class InGameController {

    private final InGameAuthenticationService authenticationService;

    // in-game 인증키 발급 요청 (Client to Server)
    @GetMapping("/v1/ingame/authentication")
    public ResponseEntity<?> getInGameTemporaryAuthenticationKey(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUserId();
        return new ResponseEntity<>(authenticationService.createTemporaryAuthenticationKey(userId), HttpStatus.OK);
    }

    // 인증키 확인 요청 (in-game to Server)
    @PostMapping("/v1/ingame/authentication")
    public ResponseEntity<?> authenticateInGameTemporaryAuthenticationKey(@RequestBody InGameAuthenticationKeyParam inGameAuthenticationKeyParam) {
        InGameAuthenticatedKeyVo response = authenticationService.authenticateTemporaryAuthenticationKey(inGameAuthenticationKeyParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
