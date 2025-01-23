package net.lodgames.version.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.version.service.AppVersionService;
import org.springframework.http.ResponseEntity;
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
