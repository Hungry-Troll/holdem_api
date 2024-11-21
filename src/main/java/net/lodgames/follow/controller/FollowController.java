package net.lodgames.follow.controller;


import lombok.AllArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.follow.param.FollowListParam;
import net.lodgames.follow.param.FollowParam;
import net.lodgames.follow.param.UnFollowParam;
import net.lodgames.follow.service.FollowService;
import net.lodgames.follow.vo.FollowVo;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.follow.vo.FolloweeVo;
import net.lodgames.follow.vo.FollowerVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class FollowController {

    private final FollowService followService;

    // 팔로워 리스트 나를 팔로우 하고 있는 사람들(follower)
    @GetMapping("/followers")
    public ResponseEntity<List<FollowerVo>> follower(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        return ResponseEntity.ok(followService.follower(FollowListParam.builder()
                .userId(userPrincipal.getUserId())
                .build()));
    }

    // 팔로잉 내가 팔로우 하고 있는 사람들(followee)
    @GetMapping("/following")
    public ResponseEntity<List<FolloweeVo>> following(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        return ResponseEntity.ok(followService.following(FollowListParam.builder()
                .userId(userPrincipal.getUserId())
                .build()));
    }

    // 팔로우함
    @PostMapping("/follow/{followId}")
    public ResponseEntity<?> follow(@PathVariable(name="followId") long followId, @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        followService.follow(FollowParam.builder()
                .userId(userPrincipal.getUserId())
                .followId(followId).build());
        return ResponseEntity.ok().build();
    }

    // 팔로우 취소
    @PostMapping("/unfollow/{followId}")
    public ResponseEntity<?> unFollow(@PathVariable(name="followId") long followId, @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        followService.unfollow(UnFollowParam.builder()
                .userId(userPrincipal.getUserId())
                .followId(followId).build());
        return ResponseEntity.ok().build();
    }


}
