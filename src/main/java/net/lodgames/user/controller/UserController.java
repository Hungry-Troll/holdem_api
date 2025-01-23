package net.lodgames.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.user.param.SearchUserNicknameParam;
import net.lodgames.user.param.UserInfoParam;
import net.lodgames.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1")
public class
UserController {

    private final UserService userService;

    // 유저 정보 조회
    @GetMapping("/users/{targetUserId}")
    public ResponseEntity<?> userInfo(@PathVariable(name="targetUserId") Long targetUserId,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userService.userInfo(
                UserInfoParam.builder()
                    .targetUserId(targetUserId)
                    .userId((userPrincipal.getUserId())).build())
        );
    }

    // 유저 닉네임 검색
    @GetMapping("/users/search/nickname")
    public ResponseEntity<?> searchUserNickname(@RequestBody SearchUserNicknameParam searchUserNicknameParam,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userService.searchUserNickname(
                SearchUserNicknameParam.builder()
                        .userId(userPrincipal.getUserId())
                        .nickname(searchUserNicknameParam.getNickname()).build())
        );
    }
}
