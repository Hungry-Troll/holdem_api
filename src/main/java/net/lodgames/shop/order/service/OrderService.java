package net.lodgames.shop.order.service;

import lombok.RequiredArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.deposit.service.DepositRecordService;
import net.lodgames.shop.order.constants.OrderStatus;
import net.lodgames.shop.order.model.Orders;
import net.lodgames.shop.order.param.CancelOrderParam;
import net.lodgames.shop.order.param.OrderAcceptParam;
import net.lodgames.shop.order.repository.OrderRepository;
import net.lodgames.shop.order.state.OrderStatePackage;
import net.lodgames.shop.order.util.OrderMapper;
import net.lodgames.shop.order.vo.OrderVo;
import net.lodgames.shop.product.model.Product;
import net.lodgames.shop.product.repository.ProductRepository;
import net.lodgames.shop.product.util.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderStatePackage orderStatePackage;
    private final ProductRepository productRepository;
    private final DepositRecordService depositRecordService;
    private final ProductMapper productMapper;

    // 주문 접수
    @Transactional(rollbackFor = Exception.class)
    public OrderVo acceptOrder(OrderAcceptParam orderAcceptParam) {
        // get Product
        Product product = productRepository.findById(orderAcceptParam.getProductId())
                .orElseThrow(() -> new RestException(ErrorCode.FAIL_ORDER_PRODUCT_NOT_EXIST)); // 주문 상품 없음
        // check [total purchase amount] within this month
        depositRecordService.checkTotalPurchaseAmountWithinThisMonth(orderAcceptParam.getUserId(), product.getPrice());
        // 상품 갯수 체크
        checkOrderAvailable(product.getStockQuantity());

        // make order
        Orders orders = orderRepository.save(Orders.builder()
                .userId(orderAcceptParam.getUserId())      // 유저 고유번호
                .originPrice(product.getOriginPrice())     // 원가격 (= 상품 원가격)
                .paymentPrice(product.getPrice())          // 지불 가격 (= 상품 가격)
                .productId(product.getId())                // 상품 (product entity)
                .orderStatus(OrderStatus.ACCEPT)           // 주문 접수
                .build());
        OrderVo orderVo = orderMapper.updateOrderToVo(orders);
        orderVo.setProductVo(productMapper.updateProductToVo(product));
        return orderVo;
    }

    private void checkOrderAvailable(Integer productCount) {
        // 상품 갯수가 null 이 아니면 상품 갯수가 제한된 상품 이다.
        if (productCount != null && productCount <= 0) {
            throw new RestException(ErrorCode.PRODUCT_IS_NOT_AVAILABLE);
        }
    }

    // 주문 구매 처리
    public void payOrder(Long orderId) {
        // 구매 처리
        handleOrderStatus(orderId, OrderStatus.PAID);
        /*
         * ACCEPT 상태에서 PAID 상태로 변경
         * 지불 기록 생성 (재화일 경우)
         * 상품 처리
         * 상품옵션 처리
         */
    }

    // 주문 취소
    @Transactional(rollbackFor = Exception.class)
    public OrderVo cancelOrder(CancelOrderParam cancelOrderParam) {
        // 상품 상태 변경 (취소)
        Orders orders = handleOrderStatus(cancelOrderParam.getOrderId(), OrderStatus.CANCEL);
        OrderVo orderVo = orderMapper.updateOrderToVo(orders);
        Product product = retrieveProduct(orders.getProductId());
        orderVo.setProductVo(productMapper.updateProductToVo(product));
        return orderVo;
    }

    // 주문 환불
    public void refundOrder(Long orderId) {
        handleOrderStatus(orderId, OrderStatus.REFUND);
    }

    // 주문 상태 변경 처리
    private Orders handleOrderStatus(Long orderId, OrderStatus orderStatus) {
        // 주문 정보 취득
        Orders orders = retrieveOrder(orderId);
        // 주문 상태 변경
        orderStatePackage.changeStateByStatus(orders, orderStatus);
        // 변경 사항 저장
        orderRepository.save(orders);
        return orders;
    }

    // 주문 정보 취득
    public Orders retrieveOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_ORDER));
    }

    // 상품 조회
    public Product retrieveProduct(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RestException(ErrorCode.PRODUCT_NOT_EXIST));
    }
}
