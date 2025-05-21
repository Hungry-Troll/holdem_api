package net.lodgames.payment.google.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.payment.google.vo.GooglePaymentReceiptParsedVo;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class GooglePaymentUtil {

    private final GoogleCredentials credentials;

    public String getAccessToken() throws IOException {
        if (credentials == null) {
            throw new RestException(ErrorCode.GOOGLE_CREDENTIAL_NOT_FOUND);
        }
        // 유효한 액세스 토큰이 있으면 그대로 사용
        if (isAccessTokenValid()) {
            return credentials.getAccessToken().getTokenValue();
        }
        return refreshAccessToken();
    }

    // receipt 파싱 예외처리용
    public GooglePaymentReceiptParsedVo receiptParser(String receipt) {
        try {
            return processParser(receipt);
        } catch (JsonProcessingException e) {
            throw new RestException(ErrorCode.GOOGLE_RECEIPT_PARSE_FAILED);
        }
    }

    // receipt JSON 파싱
    private GooglePaymentReceiptParsedVo processParser(String receipt) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // String으로 받은 JSON을 JsonNode로 파싱
        JsonNode receiptNode = objectMapper.readTree(receipt);
        // Payload 부분 추출
        String payload = receiptNode.get("Payload").asText();
        // Payload를 다시 파싱
        JsonNode payloadNode = objectMapper.readTree(payload);
        // JSON 필드 추출
        String jsonData = payloadNode.get("json").asText();
        // 최종 구매 정보 추출
        JsonNode purchaseData = objectMapper.readTree(jsonData);
        // 필요한 정보 추출
        String packageName = purchaseData.get("packageName").asText();
        String productId = purchaseData.get("productId").asText();
        String purchaseToken = purchaseData.get("purchaseToken").asText();
        String orderId = purchaseData.get("orderId").asText();

        return GooglePaymentReceiptParsedVo.builder()
                .packageName(packageName)
                .productId(productId)
                .purchaseToken(purchaseToken)
                .orderId(orderId)
                .build();
    }

    private Boolean isAccessTokenValid() {
        // 토큰과 만료 시간이 null이 아닌 경우에만 유효성 검사
        return credentials.getAccessToken() != null &&
               credentials.getAccessToken().getExpirationTime() != null &&
               credentials.getAccessToken().getExpirationTime().getTime() > System.currentTimeMillis();
    }

    // 갱신 후 엑세스 토큰 발급
    private String refreshAccessToken() throws IOException {
        try {
            credentials.refreshIfExpired();
        } catch (IOException e) {
            throw new RestException(ErrorCode.GOOGLE_ACCESS_TOKEN_REFRESH_FAILED);
        }
        return credentials.getAccessToken().getTokenValue();
    }
}
