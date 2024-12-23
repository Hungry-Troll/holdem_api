package net.lodgames.shop.product.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductOptionServiceTest {
    @InjectMocks
    ProductOptionService service;

    @Test
    void makeOrderIdempotentKey() {
        Long oderId = 1L;
        String idempotentKey1 = service.makeOrderIdempotentKey(oderId);
        String idempotentKey2 = service.makeOrderIdempotentKey(oderId);
        assertEquals(idempotentKey1, idempotentKey2);
    }
}