package net.lodgames.storage.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.storage.constants.StorageContentType;
import net.lodgames.storage.constants.StorageCurrencyType;
import net.lodgames.storage.constants.StorageSenderType;
import net.lodgames.storage.constants.StorageStatus;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
public class StorageReadVo {
    //Storage
    @JsonIgnore
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
    //StorageCurrency
    private Long currencyAmount;
    private StorageCurrencyType currencyType;
    //StorageItem
    private Long itemId;
    private Integer itemNum;
    //StorageBundle
    private Long bundleId;
    
    // StorageCurrency / StorageItem / StorageBundle 은 없으면 null 반환 됨
    // 클라와 상의 후 필요한 내용만 반환받기를 원하면 @JsonInclude(JsonInclude.Include.NON_NULL) 처리 필요
}
