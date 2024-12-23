package net.lodgames.shop.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.shop.item.param.ItemListParam;
import net.lodgames.shop.item.param.ItemParam;
import net.lodgames.shop.item.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ItemController {

    private final ItemService itemService;

    // 아이템 목록 조회
    @GetMapping("/items")
    public ResponseEntity<?> getItemList(@RequestBody ItemListParam itemListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        itemListParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(itemService.getItemList(itemListParam));
    }

    // 아이템 상세 조회
    @GetMapping("/items/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable("itemId") Long itemId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(itemService.getItem(ItemParam.builder()
                .itemId(itemId)
                .userId(userPrincipal.getUserId())
                .build()));
    }

}
