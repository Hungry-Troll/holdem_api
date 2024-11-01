package net.lodgames.shop.collection.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.collection.constants.CollectPeriodType;
import net.lodgames.shop.collection.constants.CollectionActivation;
import net.lodgames.shop.collection.model.Collection;
import net.lodgames.shop.collection.param.CollectionListParam;
import net.lodgames.shop.collection.repository.CollectionQueryRepository;
import net.lodgames.shop.collection.repository.CollectionRepository;
import net.lodgames.shop.collection.vo.CollectionVo;
import net.lodgames.shop.item.constants.ItemPeriodType;
import net.lodgames.shop.item.model.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final CollectionQueryRepository collectionQueryRepository;

    // 1. 아이템 수집.
    public Collection addCollection(Item item, Long purchaseId, long userId) {
        // 1.1. 아이템 기간 타입에 따른 보유 기간 타입
        CollectPeriodType periodType = getPeriodType(item.getPeriodType());
        // 1.2. 아이템 기간 타입에 따른 만료기한
        LocalDateTime expireDatetime = calcExpireDate(item.getPeriodType(), item.getPeriod(), item.getExpiration());
        return collectionRepository.save(Collection.builder()
                .userId(userId)                 // 유저 고유번호
                .itemId(item.getId())           // 아이템
                .purchaseId(purchaseId)         // 구매 아이디
                .expireDatetime(expireDatetime) // 만료기한
                .activation(CollectionActivation.DEACTIVATION) // 활성화 여부 (비활성화)
                .periodType(periodType)         // 기간 타입
                .build()
        );
    }

    // 1.1. 아이템 기간 타입에 따른 보유 기간 타입
    private CollectPeriodType getPeriodType(ItemPeriodType itemPeriodType) {
        return switch (itemPeriodType) {
            case DAY,MONTH, EXPIRATION -> CollectPeriodType.EXPIRATION; //
            case NONE -> CollectPeriodType.NONE;                  //
        };
    }

    // 1.2. 아이템 기간 타입에 따른 만료기한
    private LocalDateTime calcExpireDate(ItemPeriodType itemPeriodType,
                                         Integer period,
                                         LocalDateTime expiration) {
        return switch (itemPeriodType) {
            case DAY -> LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)
                    .plusDays(period);
            case MONTH -> LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)
                    .plusMonths(period);
            case EXPIRATION -> expiration;
            case NONE -> null;
        };
    }

    @Transactional
    public void deleteCollection(Long collectionId, long userId) {
        Collection collection = collectionRepository.findByIdAndUserId(collectionId, userId)
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_COLLECTION));
        // TODO check collection deletion available
        // 1. 다 사용한 아이템인지
        // 2. 기간이 만료된 아이템인지
        collectionRepository.delete(collection);
    }

    @Transactional(readOnly = true)
    public List<CollectionVo> getCollectionList(CollectionListParam collectionListParam) {
        return collectionQueryRepository.getCollectionList(collectionListParam, collectionListParam.of());
    }

}
