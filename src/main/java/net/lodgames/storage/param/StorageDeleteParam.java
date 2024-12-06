package net.lodgames.storage.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class StorageDeleteParam {
    private Long storageId;
    private Long userId;
}
