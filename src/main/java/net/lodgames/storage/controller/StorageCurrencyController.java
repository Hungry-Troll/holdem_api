package net.lodgames.storage.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.storage.constants.StorageSenderType;
import net.lodgames.storage.param.currency.StorageReceiveCurrencyParam;
import net.lodgames.storage.param.currency.StorageGrantCurrencyParam;
import net.lodgames.storage.param.currency.StorageSendCurrencyParam;
import net.lodgames.storage.service.StorageCurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class StorageCurrencyController {

    private StorageCurrencyService storageCurrencyService;

    // 보관함 재화 보내기(user -> user) create
    @PostMapping("/storages/currency/send")
    public ResponseEntity<?> sendCurrencyStorage(@RequestBody StorageSendCurrencyParam storageSendCurrencyParam,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        storageSendCurrencyParam.setSenderId(userPrincipal.getUserId());
        storageSendCurrencyParam.setSenderType(StorageSenderType.USER);
        storageCurrencyService.sendCurrencyStorage(storageSendCurrencyParam);
        return ResponseEntity.ok().build();
    }

    // 보관함 재화 보내기(admin -> user)
    @PostMapping("/storages/currency/grant")
    public ResponseEntity<?> grantCurrencyStorage(@RequestBody StorageGrantCurrencyParam storageGrantCurrencyParam){
        storageGrantCurrencyParam.setSenderId(-1L); // TODO 임시 어드민 ID : -1
        storageGrantCurrencyParam.setSenderType(StorageSenderType.ADMIN);
        storageCurrencyService.grantCurrencyStorage(storageGrantCurrencyParam);
        return ResponseEntity.ok().build();
    }

    // 보관함 재화 받기 // 민감한 데이터이므로 @PathVariable 대신 @RequestBody 사용
    @PutMapping("/storages/currency")
    public ResponseEntity<?> receiveCurrencyStorage(@RequestBody StorageReceiveCurrencyParam storageReceiveCurrencyParam,
                                                    @AuthenticationPrincipal UserPrincipal userPrincipal) {
        storageReceiveCurrencyParam.setReceiverId(userPrincipal.getUserId());
        storageCurrencyService.receiveCurrencyStorage(storageReceiveCurrencyParam);
        return ResponseEntity.ok().build();
    }
}
