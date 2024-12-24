package net.lodgames.shop.purchase.vo;

import net.lodgames.shop.item.vo.ItemVo;

public class ItemOfPurchaseFilter {
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemVo vo)) return false;
        return vo.getId() == null;
    }
}
