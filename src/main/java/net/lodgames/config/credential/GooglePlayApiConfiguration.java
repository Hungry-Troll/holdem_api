package net.lodgames.config.credential;

import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
// 런타임 환경변수 사용 시 참고
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 런타임 환경변수 사용 시 참고
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
import java.io.IOException;
import java.util.Objects;

@Configuration
@Slf4j
public class GooglePlayApiConfiguration {
    // TODO resources 경로에 json 파일 추가 및 getResourceAsStream("/파일명.json"))) 경로 수정
    // TODO 깃허브에 json 업로드 금지
    // TODO 추후 json 파일, 회사 계정으로 생성 후 교체

    // TODO 배포 시 젠킨스로 환경변수 설정 후 사용 예정
    @Bean
    public GoogleCredentials createCredentials() {
        try {
            return GoogleCredentials.fromStream(Objects.requireNonNull(getClass().getResourceAsStream("/google-service-account.json")))
                    .createScoped("https://www.googleapis.com/auth/androidpublisher");
        }
        catch (IOException e) {
            throw new RestException(ErrorCode.GOOGLE_SERVICE_ACCOUNT_NOT_FOUND);
        }
    }

    // 런타임 환경변수 사용 시 참고
//    @Value("${google.service-account.path}") // application.properties에서 이미 설정 된 환경변수 경로를 가지고 옴
//    private String googleServiceAccountPath;
//
//    @Bean
//    public GoogleCredentials createCredentials() {
//        log.info("googleServiceAccountPath: {}", googleServiceAccountPath);
//
//        try (FileInputStream credentialsStream = new FileInputStream(googleServiceAccountPath)) {
//            return GoogleCredentials.fromStream(credentialsStream)
//                    .createScoped("https://www.googleapis.com/auth/androidpublisher");
//        } catch (IOException e) {
//            throw new RestException(ErrorCode.GOOGLE_SERVICE_ACCOUNT_NOT_FOUND);
//        }
//    }
}
