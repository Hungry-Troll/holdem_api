package net.lodgames.storage.service;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.currency.chip.service.ChipService;
import net.lodgames.currency.coin.service.CoinService;
import net.lodgames.currency.diamond.service.DiamondService;
import net.lodgames.storage.constants.StorageStatus;
import net.lodgames.storage.model.Storage;
import net.lodgames.storage.model.StorageCurrency;
import net.lodgames.storage.param.currency.StorageCurrencyParam;
import net.lodgames.storage.param.currency.StorageReceiveCurrencyParam;
import net.lodgames.storage.param.currency.StorageSendCurrencyParam;
import net.lodgames.storage.repository.currency.StorageCurrencyRepository;
import net.lodgames.storage.repository.StorageRepository;
import net.lodgames.storage.service.temp.TempCoinService;
import net.lodgames.storage.service.temp.TempDiamondService;
import net.lodgames.storage.service.util.StorageValidatorService;
import net.lodgames.storage.util.StorageHashGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StorageCurrencyService {

    private final StorageRepository storageRepository;
    private final StorageCurrencyRepository storageCurrencyRepository;

    // Storage id null 체크, 삭제 체크 공용
    private final StorageValidatorService storageValidatorService;
    // MD5 해시 생성용
    private final StorageHashGenerator storageHashGenerator;
    // 재화 관련
    private final CoinService coinService;
    private final DiamondService diamondService;
    private final ChipService chipService;

    // TODO : 임시 코드 팀장님 재화 코드 사용 후 삭제
    private final TempCoinService tempCoinService;// 임시 코드
    private final TempDiamondService tempDiamondService; // 임시 코드

    // 보관함 재화 보내기(user -> user)
    @Transactional(rollbackFor = Exception.class)
    public void sendCurrencyStorage(StorageCurrencyParam storageCurrencyParam) {
        checkSendCurrencyParam(storageCurrencyParam); // 널 체크
        Storage savedStorage = recordSendCurrency(storageCurrencyParam); // 재화 보낸 기록 저장
        sendCurrency(savedStorage, storageCurrencyParam); // 재화 보내기 계산
    }

    // 보관함 재화 보내기(admin -> user)
    @Transactional(rollbackFor = Exception.class)
    public void grantCurrencyStorage(StorageCurrencyParam storageCurrencyParam) {
        checkSendCurrencyParam(storageCurrencyParam); // 널 체크
        Storage savedStorage = recordSendCurrency(storageCurrencyParam); // 재화 보낸 기록 저장
        sendCurrencyAdmin(savedStorage, storageCurrencyParam); // 재화 보내기 계산
    }

    // 보관함 재화 보내기 Param null 체크
    private void checkSendCurrencyParam(StorageCurrencyParam storageCurrencyParam) {
        // 필수 요소 확인
        if (storageCurrencyParam.getReceiverId() == null ||
                storageCurrencyParam.getCurrencyAmount() == null) {
            throw new RestException(ErrorCode.MISSING_REQUIRED_PARAMETER);
        }
        // 송금자 수신자 확인
        if (storageCurrencyParam.getSenderId().equals(storageCurrencyParam.getReceiverId())) {
            throw new RestException(ErrorCode.SAME_SENDER_AND_RECEIVER);
        }
        // 금액 검증 (양수 확인)
        if (storageCurrencyParam.getCurrencyAmount() <= 0) {
            throw new RestException(ErrorCode.INVALID_CURRENCY_AMOUNT);
        }
    }

    // 보관함 재화 보내기 (계산)
    public void sendCurrency(Storage savedStorage, StorageCurrencyParam storageCurrencyParam) {
        String generatedHash = storageHashGenerator.generateMD5(savedStorage.getId());
        switch (storageCurrencyParam.getCurrencyType()) {
            case COIN -> coinService.subCoinBySendStorage(storageCurrencyParam.getSenderId(),
                                                          storageCurrencyParam.getCurrencyAmount(),
                                                          generatedHash);
            case CHIP -> chipService.subChipBySendStorage(storageCurrencyParam.getSenderId(),
                                                          storageCurrencyParam.getCurrencyAmount(),
                                                          generatedHash);
            case DIAMOND -> diamondService.subDiamondBySendStorage(storageCurrencyParam.getSenderId(),
                                                                   storageCurrencyParam.getCurrencyAmount(),
                                                                   generatedHash);
        }
        storageRepository.save(savedStorage);
    }

    // 보관함 재화 보내기 (어드민) (계산)
    public void sendCurrencyAdmin(Storage savedStorage, StorageCurrencyParam storageCurrencyParam) {
        // 어드민은 재화를 가지고 있을 필요가 없으므로 따로 계산 할 필요가 없음
        // 형식을 맞추기 위한 더미 코드
        storageRepository.save(savedStorage);
    }

    // 보관함 재화 보내기 (저장)
    public Storage recordSendCurrency(StorageCurrencyParam storageCurrencyParam) {
        // Storage, StorageCurrency 둘 다 저장필요

        // Storage 저장
        Storage storage = Storage.builder()
                .receiverId(storageCurrencyParam.getReceiverId())
                .senderId(storageCurrencyParam.getSenderId())
                .title(storageCurrencyParam.getTitle())
                .description(storageCurrencyParam.getDescription())
                .senderType(storageCurrencyParam.getSenderType())
                .status(StorageStatus.WAITING)
                .contentType(storageCurrencyParam.getContentType())
                .expiryDate(LocalDateTime.now().plusWeeks(2)) // TODO 임시로 2주
                .build();
        Storage savedStorage = storageRepository.save(storage);

        // StorageCurrency 저장
        StorageCurrency storageCurrency = StorageCurrency.builder()
                .storageId(savedStorage.getId())
                .currencyType(storageCurrencyParam.getCurrencyType())
                .currencyAmount(storageCurrencyParam.getCurrencyAmount())
                .build();
        storageCurrencyRepository.save(storageCurrency);

        return savedStorage;
    }

    // 보관함 재화 받기
    @Transactional(rollbackFor = Exception.class)
    public void receiveCurrencyStorage(StorageReceiveCurrencyParam storageReceiveCurrencyParam) {
        // 보관함 id로 검색하고 없으면 ErrorCode.FAIL_STORAGE_NOT_FOUND
        // 삭제 여부 체크하고 없으면 ErrorCode.ALREADY_DELETED_STORAGE
        // 보관함 id로 receiverId 없으면 ErrorCode.NOT_MATCH_STORAGE_AND_RECEIVER
        Storage findStorage =
                storageValidatorService.checkStorageError(storageReceiveCurrencyParam.getStorageId(),
                                                          storageReceiveCurrencyParam.getReceiverId());

        // 보관함 id로 StorageCurrency 검색
        StorageCurrency findStorageCurrency = storageCurrencyRepository.findByStorageId(findStorage.getId())
                .orElseThrow(() -> new RestException(ErrorCode.FAIL_STORAGE_CURRENCY_NOT_FOUND));

        // 재화 계산을 위한 빌더 패턴
        StorageCurrencyParam storageCurrencyParam = StorageCurrencyParam.builder()
                // Storage
                .receiverId(findStorage.getReceiverId())
                // StorageCurrency
                .currencyType(findStorageCurrency.getCurrencyType())
                .currencyAmount(findStorageCurrency.getCurrencyAmount())
                .build();

        // 재화 계산
        receiveCurrency(findStorage, storageCurrencyParam);

        // 저장
        recordReceiveCurrency(findStorage,findStorageCurrency);
    }

    // 보관함 재화 받기 (계산)
    private void receiveCurrency(Storage findStorage, StorageCurrencyParam storageCurrencyParam) {
        // 해시 생성
        String generatedHash = storageHashGenerator.generateSHA1(findStorage.getId());
        switch (storageCurrencyParam.getCurrencyType()) {
            case COIN -> coinService.addCoinByReceiveStorage(storageCurrencyParam.getReceiverId(),
                                                             storageCurrencyParam.getCurrencyAmount(),
                                                             generatedHash);
            case CHIP -> chipService.addChipByReceiveStorage(storageCurrencyParam.getReceiverId(),
                                                             storageCurrencyParam.getCurrencyAmount(),
                                                             generatedHash);
            case DIAMOND -> diamondService.addDiamondByReceiveStorage(storageCurrencyParam.getReceiverId(),
                                                             storageCurrencyParam.getCurrencyAmount(),
                                                             generatedHash);
        }
    }

    // 보관함 재화 받기 (저장)
    public void recordReceiveCurrency(Storage findStorage, StorageCurrency findStorageCurrency) {
        findStorage.setStatus(StorageStatus.RECEIVED);
        findStorage.setDeletedAt(LocalDateTime.now());
        //findStorageCurrency.setCurrencyAmount(0L);
        storageRepository.save(findStorage);
        storageCurrencyRepository.save(findStorageCurrency);
    }
}
