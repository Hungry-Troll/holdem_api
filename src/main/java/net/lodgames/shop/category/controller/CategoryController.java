package net.lodgames.shop.category.controller;

import net.lodgames.shop.category.param.CategoryParam;
import net.lodgames.shop.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    // category 생성 (admin)
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody CategoryParam categoryParam){
        return ResponseEntity.ok(categoryService.createCategory(categoryParam));
    }

    // category 목록 조회
    @GetMapping("/categories")
    public ResponseEntity<?> getCategoryList(){
        return ResponseEntity.ok(categoryService.getCategoryList());
    }
}
