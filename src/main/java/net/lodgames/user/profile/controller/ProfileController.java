package net.lodgames.user.profile.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.user.profile.param.ProfileAddParam;
import net.lodgames.user.profile.param.ProfileModParam;
import net.lodgames.user.profile.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProfileController {

    private ProfileService profileService;

    // 조회
    @GetMapping("/profiles")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(profileService.getProfile(userPrincipal.getUserId()));
    }

    // 추가
    @PostMapping("/profiles")
    public ResponseEntity<?> addProfile(@RequestBody ProfileAddParam profileAddParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        profileAddParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(profileService.addProfile(profileAddParam));
    }

    // 변경
    @PutMapping("/profiles")
    public ResponseEntity<?> modProfile(@RequestBody ProfileModParam profileModParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        profileModParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(profileService.modProfile(profileModParam));
    }

    // 삭제 (로컬 테스트용 / 유저 아이디로 삭제)
    @DeleteMapping("/profiles/{profileId}")
    public ResponseEntity<?> deleteProfile(@PathVariable("profileId") Long userId) {
        return ResponseEntity.ok(profileService.deleteProfile(userId));
    }
}
