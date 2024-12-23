package net.lodgames.shop.bundle.repository;

import net.lodgames.shop.bundle.model.BundleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BundleItemRepository extends JpaRepository<BundleItem, Long> {
    List<BundleItem> findAllByBundleId(Long bundleId);
}
