package net.lodgames.storage.param.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StorageReceiveItemParam {
    @JsonIgnore
    private Long userId;
    private Long storageId;
}
