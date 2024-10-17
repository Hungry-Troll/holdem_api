package net.lodgames.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.user.param.ProfileAddParam;
import net.lodgames.user.param.ProfileModParam;
import net.lodgames.user.service.ProfileService;
import net.lodgames.user.vo.ProfileVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private ProfileService profileService;

    // 조회
    @GetMapping("/{profileId}")
    public ResponseEntity<?> getProfile(@PathVariable("profileId") Long profileId) {
        return ResponseEntity.ok(profileService.getProfile(profileId));
    }

    // 추가
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> addProfile(@RequestBody ProfileAddParam profileAddParam) {
        ProfileVo profileVo = profileService.addProfile(profileAddParam);
        return ResponseEntity.ok(profileVo);
    }

    // 변경
    @PutMapping("/{profileId}")
    @ResponseBody
    public ResponseEntity<?> modProfile(@PathVariable("profileId") Long profileId,@RequestBody ProfileModParam profileModParam) {
        profileModParam.setId(profileId);
        return ResponseEntity.ok(profileService.modProfile(profileModParam));
    }

}
