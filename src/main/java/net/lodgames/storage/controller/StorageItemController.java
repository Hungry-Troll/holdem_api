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

    // 보관함 아이템 받기
    @PutMapping("/storages/{storageId}/item")
    public ResponseEntity<?> receiveItemStorage(@PathVariable(name = "storageId") Long storageId,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        storageItemService.receiveItemStorage(StorageReceiveItemParam.builder()
                .storageId(storageId)
                .userId(userPrincipal.getUserId())
                .build());
        return ResponseEntity.ok().build();
    }
}
