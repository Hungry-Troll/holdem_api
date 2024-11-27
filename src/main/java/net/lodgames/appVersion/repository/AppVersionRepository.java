package net.lodgames.appVersion.repository;

import net.lodgames.appVersion.model.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppVersionRepository extends JpaRepository<AppVersion, Integer> {
}
