package net.lodgames.payment.google.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.payment.google.model.GooglePayment;
import net.lodgames.payment.google.repository.GooglePaymentRepository;
import net.lodgames.profile.model.Profile;
import net.lodgames.profile.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class GooglePaymentServiceTest {

    private final GooglePaymentService googlePaymentService;
    private final GooglePaymentRepository googlePaymentRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    GooglePaymentServiceTest(GooglePaymentService googlePaymentService, GooglePaymentRepository googlePaymentRepository, ProfileRepository profileRepository) {
        this.googlePaymentService = googlePaymentService;
        this.googlePaymentRepository = googlePaymentRepository;
        this.profileRepository = profileRepository;
    }

    @Test
    public void createData() {
        // given
        for (int i = 0; i < 10; i++) {
            profileRepository.save(Profile.builder()
                    .userId((long) i)
                    .nickname("nickname" + i)
                    .image("image" + i + ".png")
                    .basicImageIdx(null)
                    .build());
        }

        for (int i = 0; i < 10; i++) {
            googlePaymentRepository.save(GooglePayment.builder()
                    .userId((long) i)
                    .orderId("order" + i)
                    .productId("product" + i)
                    .googlePaymentLog("Log" + i)
                    .build());
        }
    }

    @Test
    public void test()
    {
        var nicknameVo = googlePaymentService.getPaymentByNickname("nickname3");
        var userIdVo = googlePaymentService.getPaymentByUserId(5L);
        var orderIdVo = googlePaymentService.getPaymentByOrderId("order8");

        // then
        assertThat(nicknameVo.getNickname()).isEqualTo("nickname3");
        log.info("nicknameVo.getGooglePaymentLog() : {}", nicknameVo.getGooglePaymentLog());
        log.info("nicknameVo.getOrderId() : {}", nicknameVo.getOrderId());
        log.info("nicknameVo.getProductId() : {}", nicknameVo.getProductId());
        log.info("nicknameVo.getUserId().toString() : {}", nicknameVo.getUserId().toString());
        log.info("nicknameVo.getNickname() : {}", nicknameVo.getNickname());

        assertThat(userIdVo.getGooglePaymentLog()).isEqualTo("Log5");
        assertThat(userIdVo.getUserId()).isEqualTo(5L);

        log.info("userIdVo.getGooglePaymentLog() : {}", userIdVo.getGooglePaymentLog());
        log.info("userIdVo.getOrderId() : {}", userIdVo.getOrderId());
        log.info("userIdVo.getProductId() : {}", userIdVo.getProductId());
        log.info("userIdVo.getUserId().toString() : {}", userIdVo.getUserId().toString());
        log.info("userIdVo.getNickname() : {}", userIdVo.getNickname());

        assertThat(orderIdVo.getOrderId()).isEqualTo("order8");

        log.info("orderIdVo.getGooglePaymentLog() : {}", orderIdVo.getGooglePaymentLog());
        log.info("orderIdVo.getOrderId() : {}", orderIdVo.getOrderId());
        log.info("orderIdVo.getProductId() : {}", orderIdVo.getProductId());
        log.info("orderIdVo.getUserId().toString() : {}", orderIdVo.getUserId().toString());
        log.info("orderIdVo.getNickname() : {}", orderIdVo.getNickname());
    }
}