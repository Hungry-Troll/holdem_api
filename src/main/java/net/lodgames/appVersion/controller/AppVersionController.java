package net.lodgames.appVersion.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.appVersion.service.AppVersionService;
import net.lodgames.config.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AppVersionController {

    private final AppVersionService appVersionService;

    @GetMapping("/versions")
    public ResponseEntity<?> getAppVersion(){
        return ResponseEntity.ok(appVersionService.getAppVersion());
    }

    @PostMapping("/versions")
    public ResponseEntity<?> updateAppVersion(){

        appVersionService.updateAppVersion();
        return ResponseEntity.ok().build();
    }
}
