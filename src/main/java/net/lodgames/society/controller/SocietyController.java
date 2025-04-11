package net.lodgames.society.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.society.param.*;
import net.lodgames.society.service.SocietyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SocietyController {

    private final SocietyService societyService;

    // society list
    @GetMapping("/societies")
    public ResponseEntity<?> societyList(@RequestBody SocietyListParam societyListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(societyService.getSocietyList(societyListParam));
    }

    // add society
    @PostMapping("/societies")
    public ResponseEntity<?> addSociety(@RequestBody SocietyAddParam societyAddParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyAddParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(societyService.addSociety(societyAddParam));
    }

    // mod society (name)
    @PutMapping("/societies/{societyId}")
    public ResponseEntity<?> modSociety(@PathVariable(name = "societyId") Long societyId,
                                        @RequestBody SocietyModParam societyModParam,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyModParam.setUserId(userPrincipal.getUserId());
        societyModParam.setSocietyId(societyId);
        return ResponseEntity.ok(societyService.modSociety(societyModParam));
    }

    // society info
    @GetMapping("/societies/{societyId}")
    public ResponseEntity<?> societyInfo(@PathVariable(name = "societyId") Long societyId,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(societyService.getSocietyInfo(SocietyInfoParam.builder()
                .userId(userPrincipal.getUserId())
                .societyId(societyId)
                .build()));
    }

    // remove society
    @DeleteMapping("/societies/{societyId}")
    public ResponseEntity<?> removeSociety(@PathVariable(name = "societyId") Long societyId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        societyService.removeSociety(SocietyDeleteParam.builder()
                .userId(userPrincipal.getUserId())
                .societyId(societyId)
                .build());
        return ResponseEntity.ok().build();
    }


}
