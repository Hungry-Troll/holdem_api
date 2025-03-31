package net.lodgames.society.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.society.param.member.*;
import net.lodgames.society.service.SocietyMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SocietyMemberController {

    private final SocietyMemberService societyMemberService;

    // society member list
    @GetMapping("/societies/{societyId}/members")
    public ResponseEntity<?> getSocietyMemberList(@PathVariable(name="societyId") Long societyId, @RequestBody SocietyMemberListParam societyMemberListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyMemberListParam.setUserId(userPrincipal.getUserId());
        societyMemberListParam.setSocietyId(societyId);
        return ResponseEntity.ok(societyMemberService.getSocietyMemberList(societyMemberListParam));
    }

    // invite society member
    @PostMapping("/societies/members/invite")
    public ResponseEntity<?> inviteSocietyMember(@RequestBody SocietyMemberInviteParam societyMemberInviteParam, @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        societyMemberInviteParam.setUserId(userPrincipal.getUserId());
        societyMemberService.inviteSocietyMember(societyMemberInviteParam);
        return ResponseEntity.ok().build();
    }

    // join society member
    @PostMapping("/societies/members")
    public ResponseEntity<?> joinSocietyMember(@RequestBody SocietyMemberParam societyMemberParam, @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        societyMemberParam.setUserId(userPrincipal.getUserId());
        societyMemberService.joinSocietyMember(societyMemberParam);
        return ResponseEntity.ok().build();
    }

    // allow society member ( 대기 리스트에서 수락 )
    @PostMapping("/societies/members/allow")
    public ResponseEntity<?> allowSocietyMember(@RequestBody SocietyMemberAllowParam societyMemberAllowParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyMemberAllowParam.setUserId(userPrincipal.getUserId());
        societyMemberService.allowSocietyMember(societyMemberAllowParam);
        return ResponseEntity.ok().build();
    }

    // allow society member ( ????? NO CASE )
    @PostMapping("/societies/members/accept")
    public ResponseEntity<?> acceptSocietyMember(@RequestBody SocietyMemberAcceptParam societyMemberAcceptParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyMemberAcceptParam.setUserId(userPrincipal.getUserId());
        societyMemberService.acceptSocietyMember(societyMemberAcceptParam);
        return ResponseEntity.ok().build();
    }

    // remove society member ( check leader auth)
    @DeleteMapping("/societies/members")
    public ResponseEntity<?> removeSocietyMember(@RequestBody SocietyMemberRemoveParam societyMemberParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyMemberParam.setUserId(userPrincipal.getUserId());
        societyMemberService.removeSocietyMember(societyMemberParam);
        return ResponseEntity.ok().build();
    }

    // leave society member
    @PostMapping("/societies/members/leave")
    public ResponseEntity<?> leaveSociety(@RequestBody SocietyMemberLeaveParam societyMemberParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyMemberParam.setUserId(userPrincipal.getUserId());
        societyMemberService.leaveSociety(societyMemberParam);
        return ResponseEntity.ok().build();
    }

    // 멤버 권한 변경
    @PostMapping("/societies/members/authority")
    public ResponseEntity<?> changeSocietyMemberAuthority(@RequestBody SocietyMemberAuthorityParam societyMemberAuthorityParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyMemberAuthorityParam.setUserId(userPrincipal.getUserId());
        societyMemberService.changeSocietyMemberAuthority(societyMemberAuthorityParam);
        return ResponseEntity.ok().build();
    }


}
