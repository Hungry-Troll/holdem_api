package net.lodgames.storage.param.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageReceiveItemParam {
    @JsonIgnore
    private Long userId;
    private Long storageId;
}
