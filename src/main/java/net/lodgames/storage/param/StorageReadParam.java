package net.lodgames.storage.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StorageReadParam {
    private Long storageId;
    private Long userId;
}
