CREATE TABLE `users`
(
    `user_id`    bigint      NOT NULL AUTO_INCREMENT COMMENT '유저고유번호',
    `status`     tinyint     NOT NULL DEFAULT '0' COMMENT '유저상태',
    `password`   varchar(60)          DEFAULT NULL COMMENT '패스워드',
    `mobile`     varchar(12) NOT NULL COMMENT '휴대폰번호',
    `email`      varchar(100)         DEFAULT NULL COMMENT '이메일',
    `user_type`  tinyint     NOT NULL DEFAULT '0' COMMENT '유저타입 0: 멤버 1: 홀덤',
    `login_at`   timestamp   NULL     DEFAULT NULL COMMENT '로그인시각',
    `logout_at`  timestamp   NULL     DEFAULT NULL COMMENT '로그아웃시각',
    `created_at` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='유저';

CREATE TABLE `profile`
(
    `id`             bigint    NOT NULL AUTO_INCREMENT COMMENT '프로필 고유번호',
    `user_id`        bigint    NOT NULL COMMENT '유저고유번호',
    `nickname`       varchar(100)       DEFAULT NULL COMMENT '닉네입',
    `unique_nickname` varchar(100)       DEFAULT NULL COMMENT '유니크 닉네입',
    `image`          varchar(100)       DEFAULT NULL COMMENT '이미지 경로',
    `created_at`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='프로필';

CREATE TABLE `agreement`
(
    `id`                    bigint    NOT NULL AUTO_INCREMENT COMMENT '동의고유번호',
    `user_id`               bigint    NOT NULL COMMENT '유저고유번호',
    `agree_term`            tinyint            DEFAULT '1' COMMENT '이용약관',
    `agree_privacy`         tinyint            DEFAULT '1' COMMENT '개인정보',
    `agree_sensitive`       tinyint            DEFAULT '0' COMMENT '민감정보',
    `agree_marketing`       tinyint            DEFAULT '0' COMMENT '마케팅',
    `agree_sns`	            tinyint	NULL	   DEFAULT '0'	COMMENT 'sns 수신동의',
    `agree_email`	        tinyint	NULL	   DEFAULT '0'	COMMENT 'email 수신동의',
    `marketing_modified_at` timestamp NULL     DEFAULT NULL COMMENT '마케팅변경일자',
    `created_at`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='동의';

CREATE TABLE `device`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '기기고유번호',
    `user_id`    bigint    NOT NULL COMMENT '유저고유번호',
    `os`         tinyint COMMENT '기기os',
    `unique_id`  varchar(50)    DEFAULT NULL COMMENT '기기 유니크 아이디',
    `version`    varchar(10)    DEFAULT NULL COMMENT '디바이스 버전',
    `created_at` timestamp NOT NULL COMMENT '생성시각',
    `updated_at` timestamp NULL DEFAULT NULL COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='기기';

CREATE TABLE `drop_out_user`
(
    `id`      int         NOT NULL AUTO_INCREMENT COMMENT '탈퇴유저 고유번호',
    `user_id` bigint      NOT NULL COMMENT '유저고유번호',
    `mobile`  varchar(50) NOT NULL COMMENT '휴대폰번호',
    `email`   varchar(100)         DEFAULT NULL COMMENT '이메일',
    `drop_at` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '탈퇴일',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='탈퇴유저';

CREATE TABLE `identity_verification`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT COMMENT '본인인증 고유번호',
    `ci`         varchar(100) NOT NULL COMMENT '연계정보 고유 아이디',
    `user_id`    bigint                DEFAULT NULL COMMENT '유저 고유번호',
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='본인인증';

CREATE TABLE `jwt_record`
(
    `issue_no`         bigint    NOT NULL AUTO_INCREMENT COMMENT '발급번호',
    `user_id`          bigint    NOT NULL COMMENT '고유번호',
    `refresh_token_id` bigint    NOT NULL COMMENT '리프레쉬토큰고유번호',
    `ip_address`       varchar(20)        DEFAULT NULL COMMENT '아이피주소',
    `expire_datetime`  datetime  NOT NULL COMMENT '만료일시',
    `logout_at`        timestamp NULL     DEFAULT NULL COMMENT '로그아웃시각',
    `created_at`       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`issue_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='jwt토큰발급기록';

CREATE TABLE `refresh_token`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT COMMENT '리프레쉬토큰 고유번호',
    `user_id`         bigint       NOT NULL COMMENT '유저고유번호',
    `refresh_token`   varchar(100) NOT NULL COMMENT '리프레쉬토큰',
    `os`              tinyint      DEFAULT NULL COMMENT '기기OS',
    `expire_datetime` datetime     NOT NULL COMMENT '만료일시',
    `created_at`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='리프레시 토큰';

CREATE TABLE `social_login`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT '소셜로그인 고유번호',
    `user_id`       bigint       DEFAULT NULL COMMENT '유저고유번호',
    `provider_type` tinyint      NOT NULL COMMENT '소셜 로그인 프로바이더 종류',
    `social_id`     varchar(100) NOT NULL COMMENT '소셜 프로바이더  유저 고유 식별 아이디',
    `created_at`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='소셜 로그인';
