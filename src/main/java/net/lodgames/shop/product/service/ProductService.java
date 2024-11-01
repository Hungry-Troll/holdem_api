package net.lodgames.shop.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.product.constants.ProductStatus;
import net.lodgames.shop.product.model.Product;
import net.lodgames.shop.product.param.ProductListParam;
import net.lodgames.shop.product.repository.ProductOptionQueryRepository;
import net.lodgames.shop.product.repository.ProductQueryRepository;
import net.lodgames.shop.product.repository.ProductRepository;
import net.lodgames.shop.product.util.ProductMapper;
import net.lodgames.shop.product.vo.ProductOptionVo;
import net.lodgames.shop.product.vo.ProductVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;
    private final ProductOptionQueryRepository productOptionQueryRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductVo> getProductList(ProductListParam productListParam) {
        // 판매중인 것만 보여준다.
        productListParam.setStatus(ProductStatus.ON_SALE);
        return productQueryRepository.getProductList(productListParam, productListParam.of());
    }

    @Transactional(readOnly = true)
    public ProductVo getProduct(Long productId, long userId) {
        // 상품 조회
        Product product = retrieveProduct(productId);
        // 상품 조회 가능여부
        checkProductInfoAvailable(product);
        // 상품 정보 vo로 전환
        ProductVo productVo = productMapper.updateProductToVo(product);
        //상품 옵션 조회 및 세팅
        List<ProductOptionVo> options = productOptionQueryRepository.getProductOptionList(productId);
        productVo.setOptions(options);
        return productVo;
    }

    // 상품 조회 가능여부 확인
    public void checkProductInfoAvailable(Product product) {
        // 상품 상태 검사 ( 판매중 )
        ProductStatus productStatus = product.getStatus();
        if (productStatus != ProductStatus.ON_SALE
                && productStatus != ProductStatus.STOP_SELLING) {
            throw new RestException(ErrorCode.PRODUCT_INFO_NOT_AVAILABLE);
        }
    }

    // 상품 조회
    public Product retrieveProduct(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RestException(ErrorCode.PRODUCT_NOT_EXIST));
    }

    // 갯수 제한이 있을 경우 갯수를 변경한다.
    public void decreaseCountIfHasQuantityLimit(Long productId) {
        Product product = retrieveProduct(productId);
        product.setCount(product.getCount() - 1);
        productRepository.save(product);
    }

    // 금액 조회
    public Integer getProductPrice(Long productId) {
        Product product = retrieveProduct(productId);
        return product.getPrice();
    }
}
