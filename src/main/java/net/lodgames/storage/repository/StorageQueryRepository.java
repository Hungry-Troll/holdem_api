package net.lodgames.storage.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.storage.constants.StorageStatus;
import net.lodgames.storage.param.StorageReadParam;
import net.lodgames.storage.param.StoragesGetParam;
import net.lodgames.storage.vo.StoragesGetVo;
import net.lodgames.storage.vo.StorageReadVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static net.lodgames.storage.model.QStorage.storage;
import static net.lodgames.storage.model.QStorageBundle.storageBundle;
import static net.lodgames.storage.model.QStorageCurrency.storageCurrency;
import static net.lodgames.storage.model.QStorageItem.storageItem;

@Repository
@AllArgsConstructor
public class StorageQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 보관함 읽기
    public Optional<StorageReadVo> readStorage(StorageReadParam storageGetParam) {
        return Optional.ofNullable(
                jpaQueryFactory.select(Projections.fields(StorageReadVo.class,
                                storage.id,
                                storage.receiverId,
                                storage.senderId,
                                storage.purchaseId,
                                storage.title,
                                storage.description,
                                storage.senderType,
                                storage.status,
                                storage.contentType,
                                storage.expiryDate,
                                storage.readAt.isNotNull().as("isRead"),
                                storageCurrency.currencyAmount,
                                storageCurrency.currencyType,
                                storageCurrency.storageId,
                                storageItem.itemId,
                                storageItem.itemNum,
                                storageBundle.bundleId

                        ))
                        .from(storage)
                        .where(storage.id.eq(storageGetParam.getStorageId())
                                .and(storage.receiverId.eq(storageGetParam.getUserId())))
                        .leftJoin(storageCurrency).on(storage.id.eq(storageCurrency.storageId))
                        .leftJoin(storageBundle).on(storage.id.eq(storageBundle.storageId))
                        .leftJoin(storageItem).on(storage.id.eq(storageItem.storageId))
                        .limit(1)
                        .fetchOne()
        );
    }

    // 보관함 전체 조회
    public List<StoragesGetVo> getStorages(StoragesGetParam param, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.fields(StoragesGetVo.class,
                        storage.id,
                        storage.receiverId,
                        storage.senderId,
                        storage.title,
                        storage.description,
                        storage.status,
                        storage.contentType,
                        storage.senderType,
                        storage.expiryDate,
                        storage.readAt.isNotNull().as("isRead"),
                        storage.createdAt,
                        storage.updatedAt,
                        storage.deletedAt,
                        storageCurrency.currencyType,
                        storageCurrency.currencyAmount,
                        storageItem.itemId,
                        storageItem.itemNum,
                        storageBundle.bundleId
                ))
                .from(storage)
                .where(storage.receiverId.eq(param.getReceiverId())
                        .and(storage.deletedAt.isNull())
                        .and(storage.status.eq(StorageStatus.WAITING)))
                .leftJoin(storageCurrency).on(storage.id.eq(storageCurrency.storageId))
                .leftJoin(storageBundle).on(storage.id.eq(storageBundle.storageId))
                .leftJoin(storageItem).on(storage.id.eq(storageItem.storageId))
                .orderBy(storage.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
