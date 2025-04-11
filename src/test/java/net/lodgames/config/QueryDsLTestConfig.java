package net.lodgames.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.lodgames.shop.bundle.repository.BundleQueryRepository;
import net.lodgames.shop.category.repository.CategoryQueryRepository;
import net.lodgames.shop.collection.repository.CollectionQueryRepository;
import net.lodgames.shop.item.repository.ItemQueryRepository;
import net.lodgames.shop.item.repository.ItemUnitQueryRepository;
import net.lodgames.shop.purchase.repository.PurchaseQueryRepository;
import net.lodgames.society.repository.SocietyQueryRepository;
import net.lodgames.stuff.repository.StuffQueryRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@TestConfiguration
public class QueryDsLTestConfig {
    @PersistenceContext
    EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    @Bean
    public StuffQueryRepository stuffQueryRepository() {
        return new StuffQueryRepository(jpaQueryFactory());
    }

    @Bean
    public ItemQueryRepository itemQueryRepository() {
        return new ItemQueryRepository(jpaQueryFactory());
    }

    @Bean
    public ItemUnitQueryRepository itemUnitQueryRepository() {
        return new ItemUnitQueryRepository(jpaQueryFactory());
    }

    @Bean
    public CategoryQueryRepository categoryQueryRepository() {
        return new CategoryQueryRepository(jpaQueryFactory());
    }

    @Bean
    public BundleQueryRepository bundleQueryRepository() {
        return new BundleQueryRepository(jpaQueryFactory());
    }

    @Bean
    public PurchaseQueryRepository purchaseQueryRepository() {
        return new PurchaseQueryRepository(jpaQueryFactory());
    }

    @Bean
    public CollectionQueryRepository collectionQueryRepository() {
        return new CollectionQueryRepository(jpaQueryFactory());
    }

    @Bean
    public SocietyQueryRepository societyQueryRepository() {
        return new SocietyQueryRepository(jpaQueryFactory());
    }
}
