package net.lodgames.storage.param.currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StorageReceiveCurrencyParam {
    @JsonIgnore
    private Long receiverId;
    private Long storageId; // 보관함 id
}
