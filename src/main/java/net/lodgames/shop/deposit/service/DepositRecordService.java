package net.lodgames.shop.deposit.service;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.currency.constants.CurrencyType;
import net.lodgames.shop.deposit.constants.DepositConstants;
import net.lodgames.shop.deposit.model.DepositCurrencyRecord;
import net.lodgames.shop.deposit.model.DepositRecord;
import net.lodgames.shop.deposit.repository.DepositCurrencyRecordRepository;
import net.lodgames.shop.deposit.repository.DepositRecordQueryRepository;
import net.lodgames.shop.deposit.repository.DepositRecordRepository;
import net.lodgames.user.model.IdentityVerification;
import net.lodgames.user.repository.IdentityVerificationRepository;
import net.lodgames.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class DepositRecordService {

    private final DepositRecordRepository depositRecordRepository;
    private final DepositCurrencyRecordRepository depositCurrencyRecordRepository;
    private final UserRepository userRepository;
    private final IdentityVerificationRepository identityVerificationRepository;
    private final DepositRecordQueryRepository depositRecordQueryRepository;

    public DepositRecord addDepositRecord(Long orderId, Long productId, Integer paymentPrice, Long userId) {
        // check user exist ( DELETE after case trace)
        userRepository.findById(userId).orElseThrow(() -> new RestException(ErrorCode.USER_NOT_EXIST));

        IdentityVerification identityVerification = identityVerificationRepository.findByUserId(userId)
                // TODO get ci info from user identity verification info
                //.orElseThrow(() -> new RestException(ErrorCode.IDENTITY_VERIFICATION_NOT_EXIST)); // TODO REPLACE
                .orElse(IdentityVerification.builder().userId(userId).ci("testCi").build());
        DepositRecord depositRecord = DepositRecord.builder()
                .orderId(orderId)                 // 주문 아이디
                .userId(userId)                   // 유저 아이디
                .productId(productId)             // 상품 아이디
                .paymentPrice(paymentPrice)       // 결제 금액
                .paymentDate(LocalDateTime.now()) // 결제 일시
                .ci(identityVerification.getCi()) // CI
                .build();
        depositRecordRepository.save(depositRecord);
        return depositRecord;
    }

    // 지불 재화 기록 생성
    public void addDepositCurrencyRecord(Long depositRecordId, CurrencyType currencyType, Integer depositAmount, String idempotentKey) {
        DepositCurrencyRecord depositCurrencyRecord = DepositCurrencyRecord.builder()
                .depositRecordId(depositRecordId) // 입금 기록 아이디
                .depositAmount(depositAmount)     // 입금 금액
                .currencyType(currencyType)       // 입금 재화 타입
                .idempotentKey(idempotentKey)     // 멱등키
                .build();
        depositCurrencyRecordRepository.save(depositCurrencyRecord);
    }

    // 구매가능 여부를 위해 이번달 구매 합계 확인 (리턴 상품 가격)
    public void checkTotalPurchaseAmountWithinThisMonth(long userId , int paymentPrice) {
        // 오늘
        LocalDate today = LocalDate.now();
        // 이번달 총 결제 금액
        Integer totalAmountInMonth = depositRecordQueryRepository.getTotalPaymentPriceWithinMonth(userId, today);
        if (totalAmountInMonth == null) {
            return;
        }
        // 이미 결제 금액을 넘음
        if (totalAmountInMonth >= DepositConstants.USER_PURCHASE_AMOUNT_LIMIT_PER_MONTH) {
            throw new RestException(ErrorCode.PURCHASE_AMOUNT_LIMIT_ALREADY_EXCEEDED);
        }
        // 결제하면 결제 금액을 넘게 됨
        if(totalAmountInMonth + paymentPrice > DepositConstants.USER_PURCHASE_AMOUNT_LIMIT_PER_MONTH) {
            throw new RestException(ErrorCode.PURCHASE_AMOUNT_LIMIT_COULD_BE_EXCEEDED);
        }
    }

    // 입금 기록 및 입급 재화 기록 삭제
    public void removeDepositRecordAndDepositCurrencyRecord(Long orderId) {
        // 입금 기록 조회
        DepositRecord depositRecord = depositRecordRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RestException(ErrorCode.DEPOSIT_RECORD_NOT_EXIST));
        // 입금 기록 삭제
        depositRecordRepository.delete(depositRecord);
        // 입금 재화 기록 삭제
        depositCurrencyRecordRepository.deleteByDepositRecordId(depositRecord.getId());
    }
}
