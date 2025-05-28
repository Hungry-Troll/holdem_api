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
    private Os os;                           // WEB(0), ANDROID(1), IOS(2), OTHER(3)
    private Long receiverId;
    private String title;
    private String description;
    @JsonIgnore
    private StorageSenderType senderType;    // USER(0),ADMIN(1)
    private StorageContentType contentType;  // CURRENCY(0),ITEM(1),BUNDLE(2)
    // StorageCurrencyType
    private CurrencyType currencyType;       // DIAMOND(0),COIN(1),GOLD(2),FREE(3),EVENT(4)
    private Long currencyAmount;
}
