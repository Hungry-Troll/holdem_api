package net.lodgames.shop.category.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CategoryVo {
    private Long id;                 // 카테고리 고유번호
    private String name;             // 카테고리 이름
    private String description;      // 카테고리 설명
    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime updatedAt; // 변경일시
}
