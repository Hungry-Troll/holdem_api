package net.lodgames.shop.collection.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.shop.collection.param.CollectionListParam;
import net.lodgames.shop.collection.service.CollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CollectionController {

    private final CollectionService collectionService;

    // 컬렉션 단일 조회
    @DeleteMapping("/collections/{collectionId}")
    public ResponseEntity<?> deleteCollection(@PathVariable("collectionId") Long collectionId, @AuthenticationPrincipal UserPrincipal userPrincipal){
        collectionService.deleteCollection(collectionId, userPrincipal.getUserId());
        return ResponseEntity.ok().build();
    }

    // 컬렉션 목록 조회
    @GetMapping("/collections")
    public ResponseEntity<?> getCollectionList(@RequestBody CollectionListParam collectionListParam, @AuthenticationPrincipal UserPrincipal userPrincipal){
        collectionListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok().body(collectionService.getCollectionList(collectionListParam ));
    }
}
