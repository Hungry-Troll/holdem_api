package net.lodgames.shop.item.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;
import net.lodgames.shop.item.constants.ItemSearchType;

@Getter
@Setter
public class ItemListParam extends PagingParam {
    @JsonIgnore
    private long userId;               // 유저 고유번호
    private Long categoryId;           // 카테고리
    private ItemSearchType searchType; // 아이템 검색 타입 
    private String searchValue;        // 검색어
}
