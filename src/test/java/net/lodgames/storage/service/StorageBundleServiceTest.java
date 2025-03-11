package net.lodgames.storage.service;

import net.lodgames.storage.param.bundle.StorageGrantBundleParam;
import net.lodgames.storage.param.bundle.StorageReceiveBundleParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StorageBundleServiceTest {

    @Autowired
    private StorageBundleService StorageBundleService;

    @Test
    void grantBundleStorage() {

        // given
        StorageGrantBundleParam storageGrantBundleParam = new StorageGrantBundleParam();
        storageGrantBundleParam.setBundleId(1L);
        storageGrantBundleParam.setDescription("description");
        storageGrantBundleParam.setReceiverId(2L);
        storageGrantBundleParam.setTitle("title");
        // then
        StorageBundleService.grantBundleStorage(storageGrantBundleParam);

    }

    @Test
    void receiveBundleStorage() {

        // given
        var param = StorageReceiveBundleParam.builder()
                .storageId(1L)
                .userId(2L)
                .build();

        StorageBundleService.receiveBundleStorage(param);

    }

}