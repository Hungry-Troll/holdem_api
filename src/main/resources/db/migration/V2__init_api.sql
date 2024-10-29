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
