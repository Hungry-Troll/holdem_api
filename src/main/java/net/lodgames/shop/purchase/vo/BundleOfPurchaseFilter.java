package net.lodgames.shop.purchase.vo;

import net.lodgames.shop.bundle.vo.BundleVo;

public class BundleOfPurchaseFilter {
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BundleVo vo)) return false;
        return vo.getId() == null;
    }
}
