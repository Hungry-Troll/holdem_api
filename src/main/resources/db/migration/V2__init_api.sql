CREATE TABLE `profile`
(
    `id`              bigint    NOT NULL AUTO_INCREMENT COMMENT '프로필 고유번호',
    `user_id`         bigint    NOT NULL COMMENT '유저고유번호',
    `nickname`        varchar(100)       DEFAULT NULL COMMENT '닉네임',
    `image`           varchar(100)       DEFAULT NULL COMMENT '이미지 경로',
    `basic_image_idx` int                DEFAULT 0 COMMENT '기본 프로필 이미지',
    `created_at`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='프로필';

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
    id              bigint       NOT NULL AUTO_INCREMENT COMMENT '메세지 고유번호',
    sender_id       bigint       NOT NULL COMMENT '메세지 보낸 유저 고유번호',
    receiver_id     bigint       NOT NULL COMMENT '메시지 받은 유저 고유번호',
    content         varchar(255) NULL COMMENT '메세지 내용',
    basic_image_idx int      NULL COMMENT '기본 프로필 이미지',
    created_at      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    read_at         timestamp    NULL COMMENT '읽은시각',
    deleted_at      timestamp    NULL COMMENT '삭제시각',
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
    `id`         int         NOT NULL AUTO_INCREMENT COMMENT '게시판 고유번호',
    `title`      varchar(50) NOT NULL COMMENT '제목',
    `content`    TEXT        NOT NULL COMMENT '본문',
    `board_type` tinyint     NOT NULL COMMENT '타입',
    `status`     tinyint     NOT NULL COMMENT '상태',
    `created_at` timestamp   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
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
    `id`             bigint        NOT NULL AUTO_INCREMENT COMMENT '유저메모 고유번호',
    `user_id`        bigint        NOT NULL COMMENT '메모 작성 유저번호',
    `target_user_id` bigint        NOT NULL COMMENT '메모 대상 유저번호',
    `memo_text`      varchar(255)  NULL COMMENT '메모 내용',
    `tag`            varchar(1000) NULL COMMENT '메모 태그',
    `created_at`     timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`     timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
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
    `expiry_date`  timestamp    NULL COMMENT '수령기한',
    `read_at`      timestamp    NULL COMMENT '읽은시각',
    `created_at`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    `deleted_at`   timestamp    NULL COMMENT '삭제시각',
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

# shop
CREATE TABLE `chip`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '칩 고유번호',
    `user_id`    bigint    NOT NULL COMMENT '유저고유번호',
    `amount`     int       NOT NULL DEFAULT 0 COMMENT '금액',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='칩';

CREATE TABLE `chip_record`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '칩기록 고유번호',
    `user_id`        bigint       NOT NULL COMMENT '유저고유번호',
    `change_type`    tinyint      NOT NULL COMMENT '변경타입(add, use)',
    `change_chip`    int          NOT NULL COMMENT '변경칩',
    `result_chip`    int          NOT NULL COMMENT '결과칩',
    `change_desc`    varchar(255) NULL COMMENT '변경설명',
    `idempotent_key` varchar(255) NOT NULL COMMENT '멱등키',
    `created_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='칩기록';

CREATE TABLE `coin`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '코인고유번호',
    `user_id`    bigint    NOT NULL COMMENT '유저고유번호',
    `amount`     int       NOT NULL DEFAULT 0 COMMENT '금액',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='코인';

CREATE TABLE `coin_record`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '코인기록 고유번호',
    `user_id`        bigint       NOT NULL COMMENT '유저고유번호',
    `change_type`    tinyint      NOT NULL COMMENT '변경타입(add, use)',
    `change_coin`    int          NOT NULL COMMENT '변경코인',
    `result_coin`    int          NOT NULL COMMENT '결과코인',
    `change_desc`    varchar(255) NULL COMMENT '변경설명',
    `idempotent_key` varchar(255) NOT NULL COMMENT '멱등키',
    `created_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='코인기록';

CREATE TABLE `diamond`
(
    `id`             bigint    NOT NULL AUTO_INCREMENT COMMENT '코인고유번호',
    `user_id`        bigint    NOT NULL COMMENT '유저고유번호',
    `free_amount`    int       NOT NULL DEFAULT 0 COMMENT '무료 금액',
    `android_amount` int       NOT NULL DEFAULT 0 COMMENT '안드로이드 금액',
    `ios_amount`     int       NOT NULL DEFAULT 0 COMMENT 'IOS 금액',
    `paid_amount`    int       NOT NULL DEFAULT 0 COMMENT '구매 금액',
    `created_at`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='다이아몬드';

CREATE TABLE `diamond_record`
(
    `id`                     bigint       NOT NULL AUTO_INCREMENT COMMENT '다이아몬드기록 고유번호',
    `user_id`                bigint       NOT NULL COMMENT '유저고유번호',
    `change_type`            tinyint      NOT NULL COMMENT '변경타입(add, use)',
    `os`                     tinyint      NOT NULL COMMENT 'os(안드로이드, ios)',
    `change_diamond`         int          NOT NULL COMMENT '변경다이아몬드',
    `result_free_diamond`    int          NOT NULL DEFAULT 0 COMMENT '결과 무료 다이아몬드',
    `result_android_diamond` int          NOT NULL DEFAULT 0 COMMENT '결과 안드로이드 다이아몬드',
    `result_ios_diamond`     int          NOT NULL DEFAULT 0 COMMENT '결과 ios 다이아몬드',
    `result_paid_diamond`    int          NOT NULL DEFAULT 0 COMMENT '결과구매 다이아몬드',
    `change_desc`            varchar(255) NULL COMMENT '변경설명',
    `idempotent_key`         varchar(255) NOT NULL COMMENT '멱등키',
    `created_at`             timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='다이아몬드기록';

CREATE TABLE `product`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '상품 고유번호',
    `name`           varchar(60)  NOT NULL COMMENT '이름',
    `description`    TEXT         NULL COMMENT '설명',
    `status`         tinyint      NOT NULL DEFAULT 0 COMMENT '상태(준비중 , 판매중, 판매중지, 게시중지)',
    `thumbnail`      varchar(255) NULL COMMENT '썸네일 경로',
    `image`          varchar(255) NULL COMMENT '이미지 경로',
    `info`           varchar(255) NULL COMMENT '상품 정보',
    `type`           tinyint      NOT NULL DEFAULT 0 COMMENT '상품타입(재화)',
    `stock_quantity` int          NULL COMMENT '재고수량',
    `price`          int          NOT NULL DEFAULT 0 COMMENT '금액',
    `origin_price`   int          NOT NULL DEFAULT 0 COMMENT '원금액',
    `created_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='상품';

CREATE TABLE `product_option`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT COMMENT '상품옵션 고유번호',
    `product_id` bigint      NOT NULL COMMENT '상품 고유번호',
    `name`       varchar(60) NOT NULL COMMENT '이름',
    `type`       tinyint     NOT NULL DEFAULT 0 COMMENT '상품옵션타입(코인, 다이아몬드)',
    `quantity`   int         NOT NULL DEFAULT 1 COMMENT '수량',
    `created_at` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='상품옵션';

CREATE TABLE `orders`
(
    `id`            bigint    NOT NULL AUTO_INCREMENT COMMENT '주문 고유번호',
    `product_id`    bigint    NOT NULL COMMENT '상품 고유번호',
    `user_id`       bigint    NOT NULL COMMENT '유저고유번호',
    `payment_price` int       NOT NULL COMMENT '지불 금액',
    `origin_price`  int       NOT NULL COMMENT '원금액',
    `order_status`  tinyint   NOT NULL COMMENT '주문 상태(주문, 지불, 취소, 환불)',
    `payment_date`  datetime  NULL COMMENT '결제일',
    `created_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='주문';

CREATE TABLE `deposit_record`
(
    `id`            bigint      NOT NULL AUTO_INCREMENT COMMENT '입금기록 고유번호',
    `order_id`      bigint      NOT NULL COMMENT '주문 고유번호',
    `product_id`    bigint      NOT NULL COMMENT '상품 고유번호',
    `payment_price` int         NOT NULL COMMENT '지불금액',
    `payment_date`  date        NOT NULL COMMENT '지불일',
    `user_id`       bigint      NOT NULL COMMENT '유저고유번호',
    `ci`            varchar(50) NULL COMMENT '유저연계정보',
    `created_at`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='입금기록';

CREATE TABLE `deposit_currency_record`
(
    `id`                bigint      NOT NULL AUTO_INCREMENT COMMENT '입금재화기록 고유번호',
    `deposit_record_id` bigint      NOT NULL COMMENT '입금기록 고유번호',
    `currency_type`     tinyint     NOT NULL COMMENT '재화타입',
    `deposit_amount`    int         NULL COMMENT '입금금액',
    `idempotent_key`    varchar(20) NULL COMMENT '멱등키',
    `created_at`        timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='입급재화기록';

CREATE TABLE `payment`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT COMMENT '결제고유번호',
    `order_id`   bigint      NOT NULL COMMENT '주문고유번호',
    `user_id`    bigint      NOT NULL COMMENT '유저고유번호',
    `amount`     int         NULL COMMENT '결제금액',
    `method`     varchar(10) NULL COMMENT '결제수단',
    `created_at` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='결제';

CREATE TABLE `category`
(
    `id`          int          NOT NULL AUTO_INCREMENT COMMENT '카테고리 고유아이디',
    `name`        varchar(30)  NOT NULL COMMENT '이름',
    `description` varchar(255) NULL COMMENT '설명',
    `created_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='카테고리';


CREATE TABLE `item_unit`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '아이템유닛 고유번호',
    `sku`         varchar(100) NOT NULL COMMENT 'SKU',
    `name`        varchar(50)  NOT NULL COMMENT '이름',
    `image`       varchar(255) NULL COMMENT '이미지 경로',
    `description` varchar(255) NULL COMMENT '설명',
    `attributes`  text         NULL COMMENT '속성',
    `type`        tinyint      NULL COMMENT '유형',
    `created_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='아이템유닛';


CREATE TABLE `item`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '아이템 고유번호',
    `category_id`    int          NOT NULL COMMENT '카테고리 고유아이디',
    `item_unit_id`   bigint       NULL COMMENT '아이템 유닛 고유번호',
    `sku`            varchar(100) NULL COMMENT 'SKU',
    `unit_sku`       varchar(100) NULL COMMENT '아이템 유닛  SKU',
    `name`           varchar(50)  NOT NULL COMMENT '이름',
    `description`    TEXT         NULL COMMENT '설명',
    `num`            int          NOT NULL DEFAULT 1 COMMENT '갯수',
    `stock_quantity` int          NULL COMMENT '재고수량',
    `status`         tinyint      NOT NULL DEFAULT 0 COMMENT '상태(준비중 , 판매중, 판매중지)',
    `thumbnail`      varchar(255) NULL COMMENT '썸네일 경로',
    `image`          varchar(255) NULL COMMENT '이미지 경로',
    `info`           varchar(255) NULL COMMENT '아이템 정보',
    `period_type`    tinyint      NOT NULL COMMENT '기간타입(none, day, expiration)',
    `period`         int          NULL COMMENT '기간',
    `expiration`     datetime     NULL COMMENT '기한',
    `currency_type`  tinyint      NOT NULL DEFAULT 0 COMMENT '재화타입(코인, 다이아몬드, 무료, 이벤트)',
    `amount`         int          NOT NULL DEFAULT 0 COMMENT '재화가격',
    `created_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='아이템';

CREATE TABLE `purchase`
(
    `id`            bigint    NOT NULL AUTO_INCREMENT COMMENT '구매고유번호',
    `user_id`       bigint    NOT NULL COMMENT '유저고유번호',
    `item_id`       bigint    NULL COMMENT '아이템 고유번호',
    `bundle_id`     bigint    NULL COMMENT '번들 고유번호',
    `purchase_type` int       NULL COMMENT '구매타입',
    `currency_type` tinyint   NOT NULL COMMENT '재화타입(코인, 다이아몬드, 무료, 이벤트)',
    `paid_amount`   int       NOT NULL COMMENT '지불금액',
    `canceled_at`   timestamp NULL COMMENT '취소시각',
    `created_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='구매';

CREATE TABLE `collection`
(
    `id`              bigint    NOT NULL AUTO_INCREMENT COMMENT '보유 고유번호',
    `item_id`         bigint    NOT NULL COMMENT '아이템 고유번호',
    `user_id`         bigint    NOT NULL COMMENT '유저고유번호',
    `purchase_id`     bigint    NOT NULL COMMENT '구매고유번호',
    `period_type`     tinyint   NOT NULL COMMENT '기간타입(영구, 기간)',
    `activation`      tinyint   NOT NULL DEFAULT 0 COMMENT '활성여부(활성,비활성)',
    `expire_datetime` datetime  NULL COMMENT '만료기한',
    `created_at`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='보유';

CREATE TABLE `bundle`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT '번들 고유번호',
    `name`             varchar(60)  NOT NULL COMMENT '이름',
    `sku`              varchar(100) NOT NULL COMMENT 'SKU',
    `description`      TEXT         NULL COMMENT '설명',
    `status`           tinyint      NOT NULL DEFAULT 0 COMMENT '상태( 준비중 , 판매중, 판매중지)',
    `thumbnail`        varchar(255) NULL COMMENT '썸네일 경로',
    `image`            varchar(255) NULL COMMENT '이미지 경로',
    `info`             varchar(255) NULL COMMENT '번들 정보',
    `count_per_person` int          NULL     DEFAULT 1 COMMENT '인당 구매제한 갯수',
    `sale_start_date`  datetime     NULL COMMENT '판매 시작 시각',
    `sale_end_date`    datetime     NULL COMMENT '판매 종료 시각',
    `currency_type`    tinyint      NOT NULL DEFAULT 0 COMMENT '재화타입( 코인, 다이아몬드, 무료, 이벤트)',
    `amount`           int          NOT NULL DEFAULT 0 COMMENT '재화가격',
    `origin_amount`    int          NOT NULL DEFAULT 0 COMMENT '재화 원가격',
    `created_at`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    `stock_quantity`   VARCHAR(255) NULL COMMENT '재고수량',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='번들';

CREATE TABLE `bundle_currency`
(
    `id`            bigint    NOT NULL AUTO_INCREMENT COMMENT '번들재화 고유번호',
    `bundle_id`     bigint    NOT NULL COMMENT '번들 고유번호',
    `currency_type` tinyint   NULL COMMENT '재화종류(coin)',
    `count`         int       NULL COMMENT '갯수',
    `created_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='번들재화';

CREATE TABLE `bundle_item`
(
    `bundle_id`  bigint    NOT NULL COMMENT '번들 고유번호',
    `item_id`    bigint    NOT NULL COMMENT '아이템 고유번호',
    `count`      int       NULL COMMENT '갯수',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='번들 아이템';

CREATE TABLE `user_character`
(
    `id`           bigint    NOT NULL AUTO_INCREMENT COMMENT '유저캐릭터고유번호',
    `user_id`      bigint    NOT NULL COMMENT '유저고유번호',
    `character_id` bigint    NOT NULL COMMENT '캐릭터고유번호',
    `customise_id` bigint    NOT NULL COMMENT '커스터마이즈고유번호',
    `level`        int       NOT NULL COMMENT '레벨',
    `grade`        int       NOT NULL COMMENT '등급',
    `status_index` int       NOT NULL COMMENT '스텟인덱스',
    `created_at`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='유저캐릭터';

CREATE TABLE `user_report`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '유저신고 고유번호',
    `reporter_id`    bigint       NOT NULL COMMENT '신고유저 고유번호',
    `target_user_id` bigint       NOT NULL COMMENT '신고대상유저 고유번호',
    `reason`         varchar(500) NOT NULL COMMENT '신고사유',
    `status`         tinyint      NOT NULL COMMENT '신고상태',
    `screenshot`     varchar(255) NULL COMMENT '스크린샷경로',
    `created_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='유저신고';

CREATE TABLE `inquiry`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT COMMENT '문의 고유번호',
    `user_id`    bigint       NOT NULL COMMENT '문의자 고유번호',
    `type`       tinyint      NOT NULL COMMENT '문의종류',
    `reason`     varchar(500) NULL COMMENT '문의내용',
    `screenshot` varchar(255) NULL COMMENT '스크린샷경로',
    `status`     tinyint      NOT NULL COMMENT '문의상태',
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='문의';

CREATE TABLE `stamina`
(
    `id`                     bigint    NOT NULL AUTO_INCREMENT COMMENT '스태미나 고유번호',
    `user_id`                bigint    NOT NULL COMMENT '유저 고유번호',
    `current_stamina`        int       NOT NULL COMMENT '현재스태미나',
    `max_stamina`            int       NOT NULL COMMENT '최대스태미나',
    `last_recovery_time`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '마지막회복접속시간',
    `recovery_complete_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '완전회복시간',
    `updated_at`             timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='스태미나';

CREATE TABLE society
(
    `id`         bigint       NOT NULL AUTO_INCREMENT COMMENT '모임 고유번호',
    `name`       varchar(100) NULL COMMENT '모임 이름',
    `join_type`  tinyint      NULL COMMENT '참가 방식',
    `passcode`   varchar(20)  NULL COMMENT '비밀번호',
    `image`      varchar(100) NULL COMMENT '이미지 경로',
    `back_image` varchar(100) NULL COMMENT '배경 이미지 경로',
    `info`       varchar(255) NULL COMMENT '정보',
    `tag`        varchar(255) NULL COMMENT '태그',
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='모임';

CREATE TABLE society_member
(
    `id`          bigint    NOT NULL AUTO_INCREMENT COMMENT '모임회원 고유번호',
    `user_id`     bigint    NULL COMMENT '유저 고유번호 ',
    `society_id`  bigint    NULL COMMENT '모임 고유번호',
    `member_type` tinyint   NULL COMMENT '회원 타입',
    `created_at`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='모임 회원';

CREATE TABLE society_member_wait
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '모임회원대기 고유번호',
    `user_id`    bigint    NULL COMMENT '유저 고유번호',
    `society_id` bigint    NULL COMMENT '모임 고유번호',
    `wait_type`  tinyint   NULL COMMENT '대기 유형',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='모임 회원 대기';

CREATE TABLE `chat_dm`
(
    `id`            bigint    NOT NULL AUTO_INCREMENT COMMENT '다이렉트 메시지 고유번호',
    `user_id`       bigint    NOT NULL COMMENT '유저고유번호',
    `target_id`     bigint    NOT NULL COMMENT '대상 유저고유번호',
    `leave_status`  tinyint   NOT NULL DEFAULT '1' COMMENT '떠남 상태',
    `target_status` tinyint   NOT NULL DEFAULT '0' COMMENT '친구 유저 상태',
    `created_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='다이렉트 메시지';

CREATE TABLE `chat_room`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT '채팅방 고유번호',
    `name`             varchar(255) NOT NULL COMMENT '방이름',
    `room_type`        tinyint      NOT NULL DEFAULT '1' COMMENT '방타입',
    `id_code`          varchar(20)  NOT NULL COMMENT '아이디코드',
    `secure_code`      varchar(20)  NULL COMMENT '보안코드',
    `capacity`         smallint     NOT NULL DEFAULT '10' COMMENT '정원',
    `current_user_num` smallint     NOT NULL DEFAULT '0' COMMENT '현재 인원',
    `created_at`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    `deleted_at`       timestamp    NULL COMMENT '삭제 시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='채팅방';

CREATE TABLE `chat_participant`
(
    `id`               bigint    NOT NULL AUTO_INCREMENT COMMENT '채팅 참가자 고유번호',
    `room_id`          bigint    NOT NULL COMMENT '채팅방 고유번호',
    `user_id`          bigint    NOT NULL COMMENT '유저 고유번호',
    `participant_type` tinyint   NOT NULL DEFAULT '0' COMMENT '참가자 타입',
    `created_at`       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='채팅 참가자';


CREATE TABLE `chat`
(
    `id`        bigint       NOT NULL AUTO_INCREMENT COMMENT '채팅 고유번호',
    `dest`      varchar(100) NOT NULL COMMENT '채팅방 목적지',
    `sender_id` bigint       NOT NULL COMMENT '보낸사람 고유번호',
    `dest_type` tinyint      NOT NULL DEFAULT '0' COMMENT '목적지 타입',
    `msg_type`  varchar(100) NOT NULL COMMENT '채팅방 목적지',
    `msg_body`  varchar(100) NOT NULL COMMENT '메시지 바디',
    `send_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '보낸시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='채팅';

CREATE TABLE `google_payment`
(
    `id`                 bigint       NOT NULL AUTO_INCREMENT COMMENT '구글결제 고유번호',
    `user_id`            bigint       NOT NULL COMMENT '유저 고유번호',
    `order_id`           varchar(50)  NOT NULL COMMENT '주문번호',
    `product_id`         varchar(100) NOT NULL COMMENT '상품아이디',
    `google_payment_log` varchar(500) NOT NULL COMMENT '구글결제로그',
    `created_at`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='구글결제';