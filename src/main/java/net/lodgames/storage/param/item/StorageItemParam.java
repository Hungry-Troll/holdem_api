package net.lodgames.storage.param.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.lodgames.storage.constants.StorageContentType;
import net.lodgames.storage.constants.StorageSenderType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageItemParam {
    @JsonIgnore
    // Storage
    private Long senderId;
    private Long receiverId;
    private String title;
    private String description;
    private StorageSenderType senderType;
    private StorageContentType contentType;
    // StorageItem
    private Long itemId;
    private Integer itemNum;
}
