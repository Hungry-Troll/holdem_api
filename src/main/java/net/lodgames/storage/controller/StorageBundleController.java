package net.lodgames.storage.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.storage.param.bundle.StorageReceiveBundleParam;
import net.lodgames.storage.param.bundle.StorageGrantBundleParam;
import net.lodgames.storage.service.StorageBundleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class StorageBundleController {

    private StorageBundleService storageBundleService;

    // 보관함 번들 보내기 (admin -> user)
    @PostMapping("/storages/bundle")
    public ResponseEntity<?> grantBundleStorage(@RequestBody StorageGrantBundleParam storageGrantBundleParam) {
        storageBundleService.grantBundleStorage(storageGrantBundleParam);
        return ResponseEntity.ok().build();
    }

    // 보관함 번들 받기
    @PutMapping("/storages/{storageId}/bundle")
    public ResponseEntity<?> receiveBundleStorage(@PathVariable("storageId") Long storageId,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        storageBundleService.receiveBundleStorage(StorageReceiveBundleParam.builder()
                .storageId(storageId)
                .userId(userPrincipal.getUserId())
                .build());
        return ResponseEntity.ok().build();
    }
}
