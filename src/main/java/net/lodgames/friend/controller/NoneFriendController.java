package net.lodgames.friend.controller;

import net.lodgames.config.security.UserPrincipal;
import net.lodgames.friend.param.NoneFriendInfoParam;
import net.lodgames.friend.service.NoneFriendService;
import net.lodgames.friend.vo.NoneFriendInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class NoneFriendController {

    private final NoneFriendService noneFriendService;

    @RequestMapping(value = "/friends/{noneFriendId}/none", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<NoneFriendInfoVo> getNoneFriend(@PathVariable(name ="noneFriendId") Long noneFriendId, @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        return ResponseEntity.ok(noneFriendService.getNoneFriendInfo(NoneFriendInfoParam.builder()
                .userId(userPrincipal.getUserId())
                .noneFriendId(noneFriendId)
                .build()));
    }

}
