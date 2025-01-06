package net.lodgames.storage.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.storage.param.StorageReadParam;
import net.lodgames.storage.param.StoragesGetParam;
import net.lodgames.storage.service.StorageService;
import net.lodgames.storage.param.StorageDeleteParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class StorageController {

    private StorageService storageService;

    // 보관함 단일 조회 (읽음 표시)
    @GetMapping("/storages/{storageId}")
    public ResponseEntity<?> readStorage(@PathVariable(name = "storageId") Long storageId,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(storageService.readStorage(
                StorageReadParam.builder()
                                .storageId(storageId)
                                .userId(userPrincipal.getUserId())
                                .build()));
    }

    // 보관함 전체 조회 (추후 UI 구성에 따라 반환 속성 변경)
    @GetMapping("/storages")
    public ResponseEntity<?> getStorages(@RequestBody StoragesGetParam storagesGetParam,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        storagesGetParam.setReceiverId(userPrincipal.getUserId());
        return ResponseEntity.ok(storageService.getStorages(storagesGetParam));
    }

    // 보관함 삭제 (Admin 사용)
    @DeleteMapping("/storages/{storageId}")
    public ResponseEntity<?> deleteStorage(@PathVariable(name ="storageId") Long storageId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        storageService.deleteStorage(
                StorageDeleteParam.builder()
                                    .storageId(storageId)
                                    .userId(userPrincipal.getUserId())
                                    .build());
        return ResponseEntity.ok().build();
    }
}
