package net.lodgames.shop.product.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.shop.product.constants.ProductStatus;
import net.lodgames.shop.product.constants.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
public class ProductVo {
    private Long id;                       // 상품 고유번호
    private String name;                   // 이름
    private String description;            // 설명
    private ProductStatus status;          // 상태 (준비중, 판매중, 판매중지, 게시중지)
    private String thumbnail;              // 썸네일 경로
    private String image;                  // 이미지 경로
    private String info;                   // 정보
    private ProductType type;              // 상품 종류
    private Integer count;                 // 갯수 (null 이면 무제한)
    private Integer price;                 // 판매가격
    private Integer originPrice;           // 원가격
    @JsonInclude(NON_NULL)
    private List<ProductOptionVo> options; // 상품 옵셙
    private LocalDateTime createdAt;       // 생성시각
    private LocalDateTime updatedAt;       // 변경시각
}
