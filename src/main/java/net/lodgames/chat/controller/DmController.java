package net.lodgames.chat.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.chat.param.DmAddParam;
import net.lodgames.chat.param.DmInfoParam;
import net.lodgames.chat.param.DmListParam;
import net.lodgames.chat.service.DmService;
import net.lodgames.chat.vo.DmVo;
import net.lodgames.config.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class DmController {

    private final DmService dmService;

    // 1:1 DM 리스트 조회
    @GetMapping("/dm/list")
    public ResponseEntity<List<DmVo>> getDmList(@RequestBody DmListParam dmListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        dmListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(dmService.getDmList(dmListParam));
    }

    // 지정 1:1 DM 조회
    @GetMapping("/dm/target/{targetId}")
    public ResponseEntity<DmVo> getDmInfo(@PathVariable(name = "targetId") Long targetId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        DmInfoParam dmInfoParam = DmInfoParam.builder()
                .userId(userPrincipal.getUserId())
                .targetId(targetId)
                .build();
        return ResponseEntity.ok(dmService.getDmInfo(dmInfoParam));
    }

    // 1:1 DM 생성
    @PostMapping("/dm")
    public ResponseEntity<?> addDm(@RequestBody DmAddParam dmAddParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        dmAddParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(dmService.addDm(dmAddParam));
    }


}
