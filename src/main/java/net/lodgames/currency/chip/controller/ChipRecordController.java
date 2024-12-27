package net.lodgames.currency.chip.controller;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.currency.chip.param.ChipRecordListParam;
import net.lodgames.currency.chip.service.ChipRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChipRecordController {

    private final ChipRecordService chipRecordService;

    @GetMapping("/chip/records")
    public ResponseEntity<?> getChipRecords(@RequestBody ChipRecordListParam chipRecordListParam , @AuthenticationPrincipal UserPrincipal userPrincipal){
        chipRecordListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(chipRecordService.getChipRecords(chipRecordListParam));
    }

}
