package net.lodgames.storage.param.currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.lodgames.storage.constants.StorageContentType;
import net.lodgames.storage.constants.StorageCurrencyType;
import net.lodgames.storage.constants.StorageSenderType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageCurrencyParam {
    // Storage
    @JsonIgnore
    private Long senderId;
    private Long receiverId;
    private String title;
    private String description;
    @JsonIgnore
    private StorageSenderType senderType;
    private StorageContentType contentType;
    // StorageCurrencyType
    private StorageCurrencyType currencyType;
    private Long currencyAmount;
}
