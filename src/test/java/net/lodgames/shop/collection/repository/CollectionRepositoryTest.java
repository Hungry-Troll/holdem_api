package net.lodgames.shop.collection.repository;

import jakarta.transaction.Transactional;
import net.lodgames.shop.collection.constants.CollectPeriodType;
import net.lodgames.shop.collection.constants.CollectionActivation;
import net.lodgames.shop.collection.model.Collection;
import net.lodgames.shop.item.model.Item;
import net.lodgames.shop.item.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;


@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CollectionRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Test
    @Rollback(false)
    public void save_collection_then_collection_saved() {

        Item item = itemRepository.findById(1L).get();

        Collection collection = collectionRepository.save(
                Collection.builder()
                        .userId(1L)  // 유저 고유번호
                        .itemId(item.getId())  // 아이템
                        .expireDatetime(LocalDateTime.now().plusDays(30))
                        .purchaseId(1L)
                        .activation(CollectionActivation.ACTIVATION)
                        .periodType(CollectPeriodType.EXPIRATION)
                        .build()
        );
    }


}
