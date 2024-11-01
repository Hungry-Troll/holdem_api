package net.lodgames.shop.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.security.UserPrincipal;
import net.lodgames.shop.item.param.ItemUnitListParam;
import net.lodgames.shop.item.param.ItemUnitParam;
import net.lodgames.shop.item.service.ItemUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ItemUnitController {

    private final ItemUnitService itemUnitService;

    // 아이템 유닛 목록 조회 ( API 에서는 거이 쓸일이 없음 )
    @GetMapping("/item-units")
    public ResponseEntity<?> getItemUnitList(@RequestBody ItemUnitListParam itemUnitListParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(itemUnitService.getItemUnitList(itemUnitListParam));
    }

    // 아이템 유닛 상세 조회
    @GetMapping("/item-units/{itemUnitId}")
    public ResponseEntity<?> getItem(@PathVariable(value = "itemUnitId") Long itemUnitId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(itemUnitService.getItemUnit(ItemUnitParam.builder()
                .itemUnitId(itemUnitId)
                .build()));
    }

}
