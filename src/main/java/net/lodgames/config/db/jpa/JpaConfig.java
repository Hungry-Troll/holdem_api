package net.lodgames.config.db.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "net.lodgames",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASPECTJ, pattern = "net.lodgames.*"
        )
)
public class JpaConfig {
}
