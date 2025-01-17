package net.lodgames.user.userreport.repository;

import net.lodgames.user.userreport.model.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
}
