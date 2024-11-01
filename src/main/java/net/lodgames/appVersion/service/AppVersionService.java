package net.lodgames.appVersion.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.appVersion.constants.AppVersionType;
import net.lodgames.appVersion.repository.AppVersionQueryRepository;
import net.lodgames.appVersion.vo.LatestAppVersionVo;
import net.lodgames.config.redis.RedisAppVersionRepositoryImpl;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AppVersionService {

    private final RedisAppVersionRepositoryImpl redisAppVersionRepository;
    private final AppVersionQueryRepository appVersionQueryRepository;

    //redis 캐시 가져오기
    public LatestAppVersionVo getAppVersion(){
        String versionData = redisAppVersionRepository.getAppVersion();

        if(versionData == null){
            throw new RestException(ErrorCode.APP_VERSION_NOT_EXIST);
        }
        else{
            String[] versions = versionData.split(":");
            return LatestAppVersionVo.builder()
                    .forceVersion(versions[0])
                    .induceVersion(versions[1])
                    .bundleVersion(versions[2])
                    .build();
        }
    }

    public void updateAppVersion(){
        String versionString = appVersionQueryRepository.getLatestVersionByType(AppVersionType.FORCE)
                + ":"
                +appVersionQueryRepository.getLatestVersionByType(AppVersionType.INDUCE)
                + ":"
                +appVersionQueryRepository.getLatestVersionByType(AppVersionType.BUNDLE);

        redisAppVersionRepository.setAppVersion(versionString);
    }
}
