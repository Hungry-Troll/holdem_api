package net.lodgames.shop.category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "category")
@EntityListeners({AuditingEntityListener.class})
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 카테고리 고유번호
    private String name;             // 카테고리 이름
    private String description;      // 카테고리 설명
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일

}
