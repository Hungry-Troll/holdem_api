package net.lodgames.storage.service;

import net.lodgames.currency.common.constants.CurrencyType;
import net.lodgames.storage.constants.StorageContentType;
import net.lodgames.storage.constants.StorageSenderType;
import net.lodgames.storage.param.currency.StorageCurrencyParam;
import net.lodgames.storage.param.currency.StorageReceiveCurrencyParam;
import net.lodgames.user.constants.Os;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StorageCurrencyServiceTest {

    @Autowired
    private StorageCurrencyService storageCurrencyService;

    @Test
    void sendCurrencyStorage() {
        StorageCurrencyParam storageCurrencyParam = StorageCurrencyParam.builder()
                .senderId(3L)
                .os(Os.ANDROID)
                .receiverId(1L)
                .title("테스트용")
                .description("테스트 보관함 재화 전송")
                .senderType(StorageSenderType.USER)
                .contentType(StorageContentType.CURRENCY)
                .currencyType(CurrencyType.COIN)
                .currencyAmount(100L)
                .build();

        storageCurrencyService.sendCurrencyStorage(storageCurrencyParam);
    }

    @Test
    void grantCurrencyStorage() {
        StorageCurrencyParam storageCurrencyParam = StorageCurrencyParam.builder()
                .senderId(2L)
                .os(Os.ANDROID)
                .receiverId(1L)
                .title("테스트용")
                .description("테스트 보관함 재화 전송")
                .senderType(StorageSenderType.ADMIN)
                .contentType(StorageContentType.CURRENCY)
                .currencyType(CurrencyType.COIN)
                .currencyAmount(100L)
                .build();

        storageCurrencyService.grantCurrencyStorage(storageCurrencyParam);
    }

    @Test
    void receiveCurrencyStorage() {
        StorageReceiveCurrencyParam storageReceiveCurrencyParam = StorageReceiveCurrencyParam.builder()
                .receiverId(1L)
                .storageId(4L)
                .os(Os.ANDROID)
                .build();

        storageCurrencyService.receiveCurrencyStorage(storageReceiveCurrencyParam);
    }
}