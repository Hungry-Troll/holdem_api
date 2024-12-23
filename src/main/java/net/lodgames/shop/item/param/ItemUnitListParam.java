package net.lodgames.shop.item.param;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;
import net.lodgames.shop.item.constants.ItemUnitSearchType;

@Getter
@Setter
public class ItemUnitListParam extends PagingParam {
    private ItemUnitSearchType searchType;
    private String searchValue;
}
