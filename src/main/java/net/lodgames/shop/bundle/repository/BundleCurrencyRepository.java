package net.lodgames.shop.bundle.repository;

import net.lodgames.shop.bundle.model.BundleCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BundleCurrencyRepository extends JpaRepository<BundleCurrency, Long> {
    List<BundleCurrency> findAllByBundleId(Long bundleId);
}
