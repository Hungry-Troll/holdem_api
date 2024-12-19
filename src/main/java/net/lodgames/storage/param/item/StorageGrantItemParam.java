package net.lodgames.storage.param.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageGrantItemParam {
    private Long itemId; // 아이템 아이디
    private Integer num; // 아이템 개수
    private Long receiverId; // 받을 유저 아이디
    private String title;
    private String description;
}
