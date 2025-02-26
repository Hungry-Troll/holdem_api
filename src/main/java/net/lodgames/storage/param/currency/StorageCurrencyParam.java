package net.lodgames.storage.param.currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.lodgames.currency.common.constants.CurrencyType;
import net.lodgames.storage.constants.StorageContentType;
import net.lodgames.storage.constants.StorageSenderType;
import net.lodgames.user.constants.Os;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageCurrencyParam {
    // Storage
    @JsonIgnore
    private Long senderId;
    @JsonIgnore
    private Os os;
    private Long receiverId;
    private String title;
    private String description;
    @JsonIgnore
    private StorageSenderType senderType;
    private StorageContentType contentType;
    // StorageCurrencyType
    private CurrencyType currencyType;
    private Long currencyAmount;
}
