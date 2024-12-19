CREATE TABLE `stuff`
(
    `id`            bigint    NOT NULL AUTO_INCREMENT COMMENT '물건고유번호',
    `status`        tinyint   NOT NULL DEFAULT '0' COMMENT '물건상태 대기:0, 활성:1, 비활성:2',
    `name`          varchar(60)        DEFAULT NULL COMMENT '이름',
    `description`   varchar(255)       DEFAULT NULL COMMENT '설명',
    `make_datetime` timestamp NULL     DEFAULT NULL COMMENT '만든시각',
    `created_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='물건';

CREATE TABLE `friend`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '친구 고유번호',
    `user_id`    bigint    NOT NULL COMMENT '유저고유번호',
    `friend_id`  bigint    NOT NULL COMMENT '친구 유저고유번호',
    `type`       tinyint   NOT NULL DEFAULT '0' COMMENT '친구 타입',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='친구';

CREATE TABLE `friend_block`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '친구차단 고유번호',
    `user_id`    bigint    NOT NULL COMMENT '유저고유번호',
    `friend_id`  bigint    NOT NULL COMMENT '친구 유저고유번호',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='친구차단';

CREATE TABLE `friend_request`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '친구신청 고유번호',
    `receiver`   bigint    NOT NULL COMMENT '받는사람 유저고유번호',
    `sender`     bigint    NOT NULL COMMENT '보내는사람 유저고유번호',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='친구신청';

CREATE TABLE `message`
(
    id          bigint       NOT NULL AUTO_INCREMENT COMMENT '메세지 고유번호',
    sender_id   bigint       NOT NULL COMMENT '메세지 보낸 유저 고유번호',
    receiver_id bigint       NOT NULL COMMENT '메시지 받은 유저 고유번호',
    content     varchar(255) NULL COMMENT '메세지 내용',
    created_at  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    read_at     timestamp    NULL COMMENT '읽은시각',
    deleted_at  timestamp    NULL COMMENT '삭제시각',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='메세지';

CREATE TABLE `follow`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '팔로우 고유번호',
    `user_id`    bigint    NOT NULL COMMENT '유저고유번호',
    `follow_id`  bigint    NOT NULL COMMENT '팔로하는사람 유저고유번호',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='팔로우';

CREATE TABLE `app_version`
(
    `id`             int         NOT NULL AUTO_INCREMENT COMMENT '앱 버전 고유번호',
    `version`        varchar(20) NOT NULL COMMENT '업데이트 버전',
    `version_type`   tinyint     NOT NULL COMMENT '앱 버전 타입',
    `publish_at`     timestamp   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '게시시각',
    `publish_status` tinyint     NOT NULL COMMENT '버전 출시 상태',
    `created_at`     timestamp   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`     timestamp   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='앱 버전';

CREATE TABLE `board`
(
    `id`         int          NOT NULL AUTO_INCREMENT COMMENT '게시판 고유번호',
    `title`      varchar(50)  NOT NULL COMMENT '제목',
    `content`    varchar(100) NOT NULL COMMENT '본문',
    `board_type` tinyint      NOT NULL COMMENT '타입',
    `status`     tinyint      NOT NULL COMMENT '상태',
    `image`      varchar(50)  NULL COMMENT '이미지 경로',
    `created_at` timestamp    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='게시판';

CREATE TABLE `user_block`
(
    `id`            bigint    NOT NULL AUTO_INCREMENT COMMENT '유저차단 고유번호',
    `user_id`       bigint    NOT NULL COMMENT '유저 고유번호',
    `block_user_id` bigint    NOT NULL COMMENT '차단유저 고유번호',
    `created_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='유저차단';

CREATE TABLE user_memo
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '유저메모 고유번호',
    `user_id`        bigint       NOT NULL COMMENT '메모 작성 유저번호',
    `target_user_id` bigint       NOT NULL COMMENT '메모 대상 유저번호',
    `memo_text`      varchar(255) NOT NULL COMMENT '메모 내용',
    `created_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='유저메모';

CREATE TABLE `storage`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT COMMENT '보관함고유번호',
    `receiver_id`  bigint       NOT NULL COMMENT '받은유저고유번호',
    `sender_id`    bigint       NULL COMMENT '보낸유저고유번호',
    `purchase_id`  bigint       NULL COMMENT '구매고유번호(아이템/번들 콜렉션 기록 장부)',
    `title`        varchar(50)  NULL COMMENT '제목',
    `description`  varchar(255) NULL COMMENT '내용',
    `status`       tinyint      NOT NULL COMMENT '상태 (수령 전, 수령 후)',
    `sender_type`  tinyint      NOT NULL COMMENT '전송타입(user/admin 보낸 쪽 기록)',
    `content_type` tinyint      NOT NULL COMMENT '종류(재화/아이템/번들)',
    `expiry_date`  timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '수령기한',
    `read_at`      timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '읽은시각',
    `created_at`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    `deleted_at`   timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '삭제시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='보관함';

CREATE TABLE `storage_currency`
(
    `id`              bigint    NOT NULL AUTO_INCREMENT COMMENT '보관함재화고유번호',
    `storage_id`      bigint    NOT NULL COMMENT '보관함고유번호',
    `currency_amount` bigint    NOT NULL COMMENT '재화량',
    `currency_type`   tinyint   NOT NULL COMMENT '재화종류',
    `created_at`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='보관함재화';

CREATE TABLE `storage_item`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '보관함아이템고유번호',
    `storage_id` bigint    NOT NULL COMMENT '보관함고유번호',
    `item_id`    bigint    NOT NULL COMMENT '아이템고유번호',
    `item_num`   int       NOT NULL COMMENT '갯수',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='보관함아이템';

CREATE TABLE `storage_bundle`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '보관함번들고유번호',
    `storage_id` bigint    NOT NULL COMMENT '보관함고유번호',
    `bundle_id`  bigint    NOT NULL COMMENT '번들고유번호',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='보관함번들';