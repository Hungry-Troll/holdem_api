package net.lodgames.currency.coin.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.coin.param.CoinRecordListParam;
import net.lodgames.currency.coin.service.CoinRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CoinRecordController {

    private final CoinRecordService coinRecordService;

    @GetMapping("/coin/records")
    public ResponseEntity<?> getCoinRecords(@RequestBody CoinRecordListParam coinRecordListParam , @AuthenticationPrincipal UserPrincipal userPrincipal){
        coinRecordListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(coinRecordService.getCoinRecords(coinRecordListParam));
    }

}
