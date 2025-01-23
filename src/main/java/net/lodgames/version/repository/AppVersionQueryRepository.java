package net.lodgames.version.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.version.constants.AppVersionType;
import net.lodgames.version.constants.PublishStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static net.lodgames.version.model.QAppVersion.appVersion;

@Repository
@RequiredArgsConstructor
public class AppVersionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //활성화 된 타입 별 최신 앱 버전 조회
    public String getLatestVersionByType(AppVersionType type){
        return jpaQueryFactory.select(appVersion.version)
                .from(appVersion)
                .where(
                        appVersion.versionType.eq(type),
                        appVersion.publishStatus.eq(PublishStatus.PUBLISH),
                        appVersion.publishAt.loe(LocalDateTime.now())
                )
                .orderBy(appVersion.publishAt.desc())
                .fetchFirst();
    }
}
