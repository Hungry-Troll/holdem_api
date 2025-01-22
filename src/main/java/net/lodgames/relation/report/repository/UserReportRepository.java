package net.lodgames.relation.report.repository;

import net.lodgames.relation.report.model.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
}
