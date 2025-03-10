package net.lodgames.storage.service;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.item.model.Item;
import net.lodgames.shop.item.repository.ItemRepository;
import net.lodgames.shop.purchase.service.PurchaseService;
import net.lodgames.storage.constants.StorageContentType;
import net.lodgames.storage.constants.StorageSenderType;
import net.lodgames.storage.constants.StorageStatus;
import net.lodgames.storage.model.Storage;
import net.lodgames.storage.model.StorageItem;
import net.lodgames.storage.param.item.StorageGrantItemParam;
import net.lodgames.storage.param.item.StorageItemParam;
import net.lodgames.storage.param.item.StorageReceiveItemParam;
import net.lodgames.storage.repository.StorageRepository;
import net.lodgames.storage.repository.item.StorageItemRepository;
import net.lodgames.storage.service.util.StorageValidatorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StorageItemService {

    private final StorageRepository storageRepository;
    private final StorageItemRepository storageItemRepository;
    private final PurchaseService purchaseService;
    private final ItemRepository itemRepository;
    // Storage id null 체크, 삭제 체크 공용
    private final StorageValidatorService storageValidatorService;

    // 보관함 아이템 보내기 // 구매(인벤장부)랑 연관 없음
    @Transactional(rollbackFor = Exception.class)
    public void grantItemStorage(StorageGrantItemParam storageGrantItemParam) {
        // 널체크
        if (storageGrantItemParam.getItemId() == null ||
            storageGrantItemParam.getNum() == null ||
            storageGrantItemParam.getReceiverId() == null) {
            throw new RestException(ErrorCode.MISSING_REQUIRED_PARAMETER);
        }
        if (storageGrantItemParam.getNum() <= 0) {
            throw new RestException(ErrorCode.INVALID_ITEM_NUMBER);
        }

        // 보관함에 넣을 수 있는 아이템 및 번들은 FREE, EVENT 만 가능
        // 해당 아이템의 CurrencyState enum 중 FREE, EVENT 만 보관함에 넣을 수 있음
        Item findItem = itemRepository.findById(storageGrantItemParam.getItemId())
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_ITEM));

        // 보관함 타입이 재화면 보낼 수 없음 (아이템만 가능)
        switch (findItem.getCurrencyType()) {
            case DIAMOND:
            case COIN:
            case CHIP:
                throw new RestException(ErrorCode.FAIL_STORAGE_ITEM_NOT_ENOUGH_STOCK);
        }

        // 받을 아이템 아이디 + 아이템 유닛 아이디 두개가 맞물려있음 아이템 가지고 오면 자연스럽게 유닛 가지고 올 수 있음
        // 이미 만들어져 있는 아이템 아이디 중 해당 아이디를 보관함에 넣기만 하면 됨
        // 보관함 테이블에 기록만 함
        Storage storage = Storage.builder()
                .receiverId(storageGrantItemParam.getReceiverId())
                .senderId(-1L) // TODO Admin 임의값
                //.purchaseId() 테이블에 들어가는게 아니라서 사용 x
                .title(storageGrantItemParam.getTitle())
                .description(storageGrantItemParam.getDescription())
                .senderType(StorageSenderType.ADMIN)
                .status(StorageStatus.WAITING)
                .contentType(StorageContentType.ITEM)
                .expiryDate(LocalDateTime.now().plusWeeks(2)) // TODO 임시로 2주
                .build();
        storageRepository.save(storage); // StorageId 필요

        // 보관함 아이템 테이블에 기록만 함
        storageItemRepository.save(StorageItem.builder()
                .storageId(storage.getId()) //StorageId 사용
                .itemId(storageGrantItemParam.getItemId())
                .itemNum(storageGrantItemParam.getNum())
                .build());
    }

    // 보관함 아이템 받기 // 구매(인벤장부)랑 연관 있음
    @Transactional(rollbackFor = Exception.class)
    public void receiveItemStorage(StorageReceiveItemParam storageReceiveItemParam) {
        // 널체크
        // 보관함 id로 검색하고 없으면 ErrorCode.FAIL_STORAGE_NOT_FOUND
        // 삭제 여부 체크하고 없으면 ErrorCode.ALREADY_DELETED_STORAGE
        // 보관함 id로 receiverId 없으면 ErrorCode.NOT_MATCH_STORAGE_AND_RECEIVER
        Storage findStorage =
                storageValidatorService.checkStorageError(storageReceiveItemParam.getStorageId(),
                                                          storageReceiveItemParam.getUserId());

        // 보관함 id로 StorageItem 검색
        StorageItem findStorageItem = storageItemRepository.findByStorageId(findStorage.getId())
                .orElseThrow(() -> new RestException(ErrorCode.FAIL_STORAGE_ITEM_NOT_FOUND));

        // 아이템 계산을 위한 빌더 패턴
        StorageItemParam storageItemParam = StorageItemParam.builder()
                // Storage
                .receiverId(findStorage.getReceiverId())
                // StorageItem
                .itemId(findStorageItem.getItemId())
                .itemNum(findStorageItem.getItemNum())
                .build();

        // 아이템 받기
        receiveItem(findStorage, storageItemParam);

        // 저장
        recordGetItem(findStorage, findStorageItem);
    }

    private void receiveItem(Storage findStorage, StorageItemParam storageItemParam) {
        Long purchaseId
                = purchaseService.purchaseItemByReceiveStorage(storageItemParam.getReceiverId(),
                                                               storageItemParam.getItemId());
        // 구매고유아이디
        findStorage.setPurchaseId(purchaseId);
    }

    // 보관함 아이템 받기 (저장)
    @Transactional(rollbackFor = Exception.class)
    public void recordGetItem(Storage findStorage, StorageItem findStorageItem) {
        findStorage.setStatus(StorageStatus.RECEIVED);
        findStorage.setDeletedAt(LocalDateTime.now());
        //findStorageItem.setItemNum(0); 어차피 보관함 삭제를 하니까 아이템 갯수 초기화할 필요가 있나 고민
        storageRepository.save(findStorage);
        storageItemRepository.save(findStorageItem);
    }
}
