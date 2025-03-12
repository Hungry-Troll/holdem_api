package net.lodgames.storage.service;

import net.lodgames.storage.param.item.StorageGrantItemParam;
import net.lodgames.storage.param.item.StorageReceiveItemParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StorageItemServiceTest {

    @Autowired
    private StorageItemService storageItemService;
    @Test
    void grantItemStorage() {
        StorageGrantItemParam storageGrantItemParam = new StorageGrantItemParam();
        storageGrantItemParam.setReceiverId(2L);
        storageGrantItemParam.setItemId(1L);
        storageGrantItemParam.setTitle("테스트용 타이틀");
        storageGrantItemParam.setDescription("테스트용 아이템 설명");
        storageGrantItemParam.setNum(1);

        storageItemService.grantItemStorage(storageGrantItemParam);
    }

    @Test
    void receiveItemStorage() {
        StorageReceiveItemParam storageReceiveItemParam
                = StorageReceiveItemParam.builder()
                        .storageId(7L)
                        .userId(2L)
                        .build();

        storageItemService.receiveItemStorage(storageReceiveItemParam);
    }
}