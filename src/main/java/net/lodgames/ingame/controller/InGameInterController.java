package net.lodgames.ingame.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.ingame.param.InGameAuthenticationKeyParam;
import net.lodgames.ingame.service.InGameAuthenticationService;
import net.lodgames.ingame.vo.InGameAuthenticatedKeyVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/inter/api")
@RestController
public class InGameInterController {

    private final InGameAuthenticationService authenticationService;

    // 인증키 확인 요청 (in-game to Server)
    @PostMapping("/v1/ingame/authentication/confirm")
    public ResponseEntity<?> authenticateInGameTemporaryAuthenticationKey(@RequestBody InGameAuthenticationKeyParam inGameAuthenticationKeyParam) {
        InGameAuthenticatedKeyVo response = authenticationService.authenticateTemporaryAuthenticationKey(inGameAuthenticationKeyParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
