package net.lodgames.shop.order.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.shop.order.constants.OrderStatus;
import net.lodgames.shop.product.vo.ProductVo;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
public class OrderVo {
    private Long id;                   // 주문 고유번호
    @JsonInclude(NON_NULL)
    private ProductVo productVo;       // 상품
    private Long userId;               // 유저 고유번호
    private Integer paymentPrice;      // 지물 가격
    private Integer originPrice;       // 원 가격
    private OrderStatus orderStatus;   // 주문 상태
    private LocalDateTime paymentDate; // 지불 시각
}
