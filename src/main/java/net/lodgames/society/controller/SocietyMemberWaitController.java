package net.lodgames.society.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.society.param.wait.SocietyMemberWaitCancelParam;
import net.lodgames.society.param.wait.SocietyMemberWaitListParam;
import net.lodgames.society.param.wait.SocietyMemberWaitRemoveParam;
import net.lodgames.society.param.wait.SocietyOwnWaitListParam;
import net.lodgames.society.service.SocietyMemberWaitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SocietyMemberWaitController {

    private final SocietyMemberWaitService societyMemberWaitService;

    // society member wait list
    @GetMapping("/societies/members/waits")
    public ResponseEntity<?> getSocietyMemberList(@RequestBody SocietyMemberWaitListParam societyMemberWaitListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyMemberWaitListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(societyMemberWaitService.getSocietyMemberWaitList(societyMemberWaitListParam));
    }

    // remove society member wait ( check leader auth )
    @DeleteMapping("/societies/members/waits")
    public ResponseEntity<?> removeSocietyMemberWait(@RequestBody SocietyMemberWaitRemoveParam societyMemberWaitRemoveParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyMemberWaitRemoveParam.setUserId(userPrincipal.getUserId());
        societyMemberWaitService.removeSocietyMemberWait(societyMemberWaitRemoveParam);
        return ResponseEntity.ok().build();
    }

    // cancel society member wait
    @PostMapping("/societies/members/waits/cancel")
    public ResponseEntity<?> cancelSocietyMemberWait(@RequestBody SocietyMemberWaitCancelParam societyMemberWaitCancelParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyMemberWaitCancelParam.setUserId(userPrincipal.getUserId());
        societyMemberWaitService.cancelSocietyMemberWait(societyMemberWaitCancelParam);
        return ResponseEntity.ok().build();
    }

    // society member's own wait list
    @GetMapping("/societies/members/waits/own")
    public ResponseEntity<?> getSocietyOwnWaitList(@RequestBody SocietyOwnWaitListParam societyOwnWaitListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyOwnWaitListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(societyMemberWaitService.getSocietyOwnWaitList(societyOwnWaitListParam));
    }

}
