package net.lodgames.storage.param.bundle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StorageReceiveBundleParam {
    @JsonIgnore
    private Long userId;
    private Long storageId;
}
