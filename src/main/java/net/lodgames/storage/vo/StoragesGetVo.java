package net.lodgames.storage.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.currency.common.constants.CurrencyType;
import net.lodgames.storage.constants.StorageContentType;
import net.lodgames.storage.constants.StorageSenderType;
import net.lodgames.storage.constants.StorageStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class StoragesGetVo {
    // TODO 추후 화면 UI 구성에 따라서 바뀔 수 있음
    // Storage
    private Long id;
    private Long receiverId;
    private Long senderId;
    @JsonIgnore
    private Long purchaseId;
    private String title;
    private String description;
    private StorageStatus status;
    private StorageSenderType senderType;
    private StorageContentType contentType;
    private LocalDateTime expiryDate;
    private Boolean isRead;
    // StorageCurrency
    private Long currencyAmount;
    private CurrencyType currencyType;
    // StorageItem
    private Long itemId;
    private Integer itemNum;
    // StorageBundle
    private Long bundleId;
}
