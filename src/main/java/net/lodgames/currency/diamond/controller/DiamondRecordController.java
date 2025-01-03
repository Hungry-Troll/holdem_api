package net.lodgames.currency.diamond.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.diamond.param.DiamondRecordListParam;
import net.lodgames.currency.diamond.service.DiamondRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DiamondRecordController {

    private final DiamondRecordService diamondRecordService;

    @GetMapping("/diamond/records")
    public ResponseEntity<?> getDiamondRecords(@RequestBody DiamondRecordListParam diamondRecordListParam , @AuthenticationPrincipal UserPrincipal userPrincipal){
        diamondRecordListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(diamondRecordService.getDiamondRecords(diamondRecordListParam));
    }

}
