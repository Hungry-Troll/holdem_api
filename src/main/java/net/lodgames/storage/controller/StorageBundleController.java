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

    // 보관함 번들 받기 // 민감한 데이터이므로 @PathVariable 대신 @RequestBody 사용
    @PutMapping("/storages/bundle")
    public ResponseEntity<?> receiveBundleStorage(@RequestBody StorageReceiveBundleParam storageReceiveBundleParam,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        storageReceiveBundleParam.setUserId(userPrincipal.getUserId());
        storageBundleService.receiveBundleStorage(storageReceiveBundleParam);
        return ResponseEntity.ok().build();
    }
}
