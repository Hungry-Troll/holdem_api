package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.RoomSearchType;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class RoomListParam extends PagingParam {
    @JsonIgnore
    private Long userId;
    private RoomSearchType searchType; // 검색어 타입
    private String searchValue; // 검색어
}
