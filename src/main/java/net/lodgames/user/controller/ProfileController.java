package net.lodgames.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.user.param.ProfileAddParam;
import net.lodgames.user.param.ProfileModParam;
import net.lodgames.user.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private ProfileService profileService;

    // 조회
    @GetMapping("")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(profileService.getProfile(userPrincipal.getUserId()));
    }

    // 추가
    @PostMapping("")
    public ResponseEntity<?> addProfile(@RequestBody ProfileAddParam profileAddParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        profileAddParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(profileService.addProfile(profileAddParam));
    }

    // 변경
    @PutMapping("")
    public ResponseEntity<?> modProfile(@RequestBody ProfileModParam profileModParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        profileModParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(profileService.modProfile(profileModParam));
    }

    // 삭제 (로컬 테스트용 / 프로필 아이디로 삭제)
    @DeleteMapping("/{profileId}")
    public ResponseEntity<?> deleteProfile(@PathVariable("profileId") Long profileId) {
        return ResponseEntity.ok(profileService.deleteProfile(profileId));
    }
}
