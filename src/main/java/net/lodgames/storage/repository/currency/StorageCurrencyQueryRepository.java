package net.lodgames.storage.repository.currency;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import net.lodgames.storage.repository.StorageRepository;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StorageCurrencyQueryRepository {

    private final StorageRepository repository;
    private final JPAQueryFactory jpaQueryFactory;


}
