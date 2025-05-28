package net.lodgames.currency.gold.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.gold.param.GoldRecordListParam;
import net.lodgames.currency.gold.service.GoldRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class GoldRecordController {

    private final GoldRecordService goldRecordService;

    @GetMapping("/gold/records")
    public ResponseEntity<?> getGoldRecords(@RequestBody GoldRecordListParam goldRecordListParam , @AuthenticationPrincipal UserPrincipal userPrincipal){
        goldRecordListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(goldRecordService.getGoldRecords(goldRecordListParam));
    }

}
