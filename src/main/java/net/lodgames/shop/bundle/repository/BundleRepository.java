package net.lodgames.shop.bundle.repository;

import net.lodgames.shop.bundle.model.Bundle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BundleRepository extends JpaRepository<Bundle, Long> {
}
