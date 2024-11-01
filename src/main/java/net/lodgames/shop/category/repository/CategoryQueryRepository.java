package net.lodgames.shop.category.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.lodgames.shop.category.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.lodgames.shop.category.model.QCategory.category;


@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<CategoryVo> getCategoryList(){
        return jpaQueryFactory
                .select(Projections.fields(CategoryVo.class,
                        category.id,          // 카테고리 고유번호
                        category.name,        // 카테고리 이름
                        category.description, // 카테고리 설명
                        category.createdAt,   // 생성일
                        category.updatedAt    // 변경일
                ))
                .from(category)
                .fetch();
    }
}
