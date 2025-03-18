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
public class StorageReadVo {
    //Storage
    @JsonIgnore
    private long id;
    private long receiverId;
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
    //StorageCurrency
    private long currencyAmount;
    private CurrencyType currencyType;
    //StorageItem
    private long itemId;
    private int itemNum;
    //StorageBundle
    private long bundleId;
    
    // StorageCurrency / StorageItem / StorageBundle 은 없으면 null 반환 됨
    // 클라와 상의 후 필요한 내용만 반환받기를 원하면 @JsonInclude(JsonInclude.Include.NON_NULL) 처리 필요
}
