package net.lodgames.storage.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.storage.param.item.StorageGrantItemParam;
import net.lodgames.storage.service.StorageItemService;
import net.lodgames.storage.param.item.StorageReceiveItemParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class StorageItemController {

    private StorageItemService storageItemService;

    // 보관함 아이템 보내기(admin -> user)
    @PostMapping("/storages/item")
    public ResponseEntity<?> grantItemStorage(@RequestBody StorageGrantItemParam storageGrantItemParam) {
        storageItemService.grantItemStorage(storageGrantItemParam);
        return ResponseEntity.ok().build();
    }

    // 보관함 아이템 받기 // 민감한 데이터이므로 @PathVariable 대신 @RequestBody 사용
    @PutMapping("/storages/item")
    public ResponseEntity<?> receiveItemStorage(@RequestBody StorageReceiveItemParam storageReceiveItemParam,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        storageReceiveItemParam.setUserId(userPrincipal.getUserId());
        storageItemService.receiveItemStorage(storageReceiveItemParam);
        return ResponseEntity.ok().build();
    }
}
