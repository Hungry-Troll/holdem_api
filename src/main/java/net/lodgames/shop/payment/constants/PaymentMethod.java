package net.lodgames.shop.payment.constants;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    APPLE("apple"),   // 애플 인앱 결제
    GOOGLE("google"), // 구글 인앱 결제 
    PG("pg");         // PG사
    private final String value;
    PaymentMethod(final String value) {
        this.value = value;
    }
}
