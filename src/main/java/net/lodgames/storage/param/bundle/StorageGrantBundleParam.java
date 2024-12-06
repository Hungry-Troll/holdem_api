package net.lodgames.storage.param.bundle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageGrantBundleParam {
    private Long bundleId; // 번들 아이디
    private Long receiverId; // 받을 유저 아이디
    private String title;
    private String description;
}
