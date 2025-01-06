package net.lodgames.storage.service;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.bundle.model.Bundle;
import net.lodgames.shop.bundle.repository.BundleRepository;
import net.lodgames.shop.item.model.Item;
import net.lodgames.shop.item.repository.ItemRepository;
import net.lodgames.shop.purchase.service.PurchaseService;
import net.lodgames.storage.constants.StorageContentType;
import net.lodgames.storage.constants.StorageSenderType;
import net.lodgames.storage.constants.StorageStatus;
import net.lodgames.storage.model.Storage;
import net.lodgames.storage.model.StorageBundle;
import net.lodgames.storage.param.bundle.StorageBundleParam;
import net.lodgames.storage.param.bundle.StorageReceiveBundleParam;
import net.lodgames.storage.param.bundle.StorageGrantBundleParam;
import net.lodgames.storage.repository.bundle.StorageBundleRepository;
import net.lodgames.storage.repository.StorageRepository;
import net.lodgames.storage.service.temp.TempBundleService;
import net.lodgames.storage.service.util.StorageValidatorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StorageBundleService {

    private final StorageRepository storageRepository;
    private final StorageBundleRepository storageBundleRepository;
    private final PurchaseService purchaseService;
    private final BundleRepository bundleRepository;

    private final StorageValidatorService storageValidatorService;

    // 보관함 번들 보내기 // 구매(인벤장부)랑 연관 없음
    @Transactional(rollbackFor = Exception.class)
    public void grantBundleStorage(StorageGrantBundleParam storageGrantBundleParam) {
        // 널체크
        if (storageGrantBundleParam.getBundleId() == null ||
            storageGrantBundleParam.getReceiverId() == null) {
            throw new RestException(ErrorCode.MISSING_REQUIRED_PARAMETER);
        }

        // 보관함에 넣을 수 있는 아이템 및 번들은 FREE, EVENT 만 가능
        // 해당 아이템의 CurrencyState enum 중 FREE, EVENT 만 보관함에 넣을 수 있음
        Bundle findBundle = bundleRepository.findById(storageGrantBundleParam.getBundleId())
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_ITEM));

        // 보관함 타입이 재화면 보낼 수 없음 (번들만 가능)
        switch (findBundle.getCurrencyType()) {
            case DIAMOND:
            case COIN:
            case CHIP:
                throw new RestException(ErrorCode.FAIL_STORAGE_BUNDLE_NOT_ENOUGH_STOCK);
        }

        // 필요한 내용 받을 사람 아이디
        // 이미 만들어져 있는 번들 아이디 중 해당 아이디를 보관함에 넣기만 하면 됨
        // 보관함 테이블에 기록만 함
        Storage storage = Storage.builder()
                .receiverId(storageGrantBundleParam.getReceiverId())
                .senderId(-1L) // Admin
                //.purchaseId() 테이블에 들어가는게 아니라서 사용 x
                .title(storageGrantBundleParam.getTitle())
                .description(storageGrantBundleParam.getDescription())
                .senderType(StorageSenderType.ADMIN)
                .status(StorageStatus.WAITING)
                .contentType(StorageContentType.BUNDLE)
                .expiryDate(LocalDateTime.now().plusWeeks(2)) //임시 2주
                .build();
        storageRepository.save(storage); // StorageId 필요

        // 보관함 번들 테이블에 기록만 함
        storageBundleRepository.save(StorageBundle.builder()
                .storageId(storage.getId()) //StorageId 사용
                .bundleId(storageGrantBundleParam.getBundleId())
                .build());
    }

    // 보관함 번들 받기 // 구매(인벤장부)랑 연관 있음
    @Transactional(rollbackFor = Exception.class)
    public void receiveBundleStorage(StorageReceiveBundleParam storageReceiveBundleParam) {
        // 널체크
        // 보관함 id로 검색하고 없으면 ErrorCode.FAIL_STORAGE_NOT_FOUND
        // 삭제 여부 체크하고 없으면 ErrorCode.ALREADY_DELETED_STORAGE
        // 보관함 id로 receiverId 없으면 ErrorCode.NOT_MATCH_STORAGE_AND_RECEIVER
        Storage findStorage =
                storageValidatorService.checkStorageError(storageReceiveBundleParam.getStorageId(),
                        storageReceiveBundleParam.getUserId());

        // 보관함 id로 StorageBundle 검색
        StorageBundle findStorageBundle = storageBundleRepository.findByStorageId(findStorage.getId())
                .orElseThrow(() -> new RestException(ErrorCode.FAIL_STORAGE_BUNDLE_NOT_FOUND));

        // 번들 계산을 위한 빌더 패턴
        StorageBundleParam storageBundleParam = StorageBundleParam.builder()
                // Storage
                .receiverId(findStorage.getReceiverId())
                // StorageBundle
                .bundleId(findStorageBundle.getBundleId())
                .build();

        // 아이템 받기
        receiveBundle(findStorage, storageBundleParam);

        // 저장
        recordGetBundle(findStorage, findStorageBundle);
    }

    private void receiveBundle(Storage findStorage, StorageBundleParam storageBundleParam) {
        Long purchaseId =
                purchaseService.purchaseBundleByReceiveStorage(storageBundleParam.getReceiverId(),
                                                               storageBundleParam.getBundleId());
        // 구매고유아이디
        findStorage.setPurchaseId(purchaseId);
    }

    // 보관함 번들 받기 (저장)
    @Transactional(rollbackFor = Exception.class)
    public void recordGetBundle(Storage findStorage, StorageBundle findStorageBundle) {
        findStorage.setStatus(StorageStatus.RECEIVED);
        findStorage.setDeletedAt(LocalDateTime.now());
        storageRepository.save(findStorage);
        storageBundleRepository.save(findStorageBundle);
    }
}
