package net.lodgames.payment.google.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.payment.google.constants.GooglePaymentPurchaseState;
import net.lodgames.payment.google.model.GooglePayment;
import net.lodgames.payment.google.repository.GooglePaymentQueryRepository;
import net.lodgames.payment.google.repository.GooglePaymentRepository;
import net.lodgames.payment.google.util.GooglePaymentUtil;
import net.lodgames.payment.google.vo.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class GooglePaymentService {
    // 서비스 계정 인증 정보
    private final GooglePaymentUtil googlePaymentUtil;
    //
    private final GooglePaymentRepository googlePaymentRepository;
    private final GooglePaymentQueryRepository googlePaymentQueryRepository;
    // Google Play Developer API URL
    private static String CONSUME_URL = "https://androidpublisher.googleapis.com/androidpublisher/v3/applications/{packageName}/purchases/products/{productId}/tokens/{token}:consume";
    private static String VERIFY_URL = "https://androidpublisher.googleapis.com/androidpublisher/v3/applications/{packageName}/purchases/products/{productId}/tokens/{token}";

    // 영수증 소비 처리
    @Transactional(rollbackFor = Exception.class)
    public GooglePaymentVerifyReceiptVo consumePurchase(String receipt, Long userId) throws IOException {
        // 영수증 파싱
        GooglePaymentReceiptParsedVo receiptParsedVo = googlePaymentUtil.receiptParser(receipt);
        // TODO DB 검증 플로우 추가

        // 액세스 토큰 가져오기
        String accessToken = googlePaymentUtil.getAccessToken();
        // Google Play API 호출
        ResponseEntity<String> response = callGoogleApi(receiptParsedVo, accessToken, CONSUME_URL, HttpMethod.POST);
        log.info("response.getStatusCode() : {}", response.getStatusCode());
        // 상태 코드 확인
        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new RestException(ErrorCode.FAILED_TO_VERIFY_PURCHASE);
        }
        return verifyReceipt(receiptParsedVo, accessToken, userId);
    }

    // Google Play API 호출
    private ResponseEntity<String> callGoogleApi(GooglePaymentReceiptParsedVo receiptParsedVo, String accessToken, String apiUrl, HttpMethod method) {
        String resultUrl = UriComponentsBuilder.fromUriString(apiUrl)
                .buildAndExpand(receiptParsedVo.getPackageName(),
                        receiptParsedVo.getProductId(),
                        receiptParsedVo.getPurchaseToken())
                .toUriString();

        // HTTP 요청 헤더에 Authorization 추가
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Google Play API 호출
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(resultUrl, method, entity, String.class);
    }

    // 영수증 검증
    private GooglePaymentVerifyReceiptVo verifyReceipt(GooglePaymentReceiptParsedVo receiptParsedVo, String accessToken, Long userId) {
        // Google Play API 호출
        ResponseEntity<String> response = callGoogleApi(receiptParsedVo, accessToken, VERIFY_URL, HttpMethod.GET);
        // 상태 코드 확인
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RestException(ErrorCode.FAILED_TO_VERIFY_PURCHASE);
        }
        // 응답 JSON 문자열
        String jsonResponse = response.getBody();
        // DB 저장
        googlePaymentRepository.save(GooglePayment.builder()
                                                    .userId(userId)
                                                    .orderId(receiptParsedVo.getOrderId())
                                                    .productId(receiptParsedVo.getProductId())
                                                    .googlePaymentLog(jsonResponse)
                                                    .build());

        return GooglePaymentVerifyReceiptVo.builder()
                .purchaseState(GooglePaymentPurchaseState.PURCHASED)
                .orderId(receiptParsedVo.getOrderId())
                .productId(receiptParsedVo.getProductId())
                .build();
    }

    // 닉네임으로 결제 정보 조회
    @Transactional(readOnly = true)
    public GooglePaymentGetNicknameVo getPaymentByNickname(String nickname) {
        return googlePaymentQueryRepository.getPaymentByNickname(nickname)
                .orElseThrow(() -> new RestException(ErrorCode.GOOGLE_PAYMENT_NOT_FOUND));
    }

    // 유저 아이디로 결제 정보 조회
    @Transactional(readOnly = true)
    public GooglePaymentGetUserIdVo getPaymentByUserId(Long userId) {
        GooglePayment findGooglePayment =  googlePaymentRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.GOOGLE_PAYMENT_NOT_FOUND));

        return GooglePaymentGetUserIdVo.builder()
                .userId(findGooglePayment.getUserId())
                .orderId(findGooglePayment.getOrderId())
                .productId(findGooglePayment.getProductId())
                .googlePaymentLog(findGooglePayment.getGooglePaymentLog())
                .build();
    }

    // 주문 아이디로 결제 정보 조회
    @Transactional(readOnly = true)
    public GooglePaymentGetOrderIdVo getPaymentByOrderId(String orderId) {
         GooglePayment findGooglePayment = googlePaymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RestException(ErrorCode.GOOGLE_PAYMENT_NOT_FOUND));

         return GooglePaymentGetOrderIdVo.builder()
                 .userId(findGooglePayment.getUserId())
                 .orderId(findGooglePayment.getOrderId())
                 .productId(findGooglePayment.getProductId())
                 .googlePaymentLog(findGooglePayment.getGooglePaymentLog())
                 .build();
    }
}
