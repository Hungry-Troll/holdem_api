package net.lodgames.shop.category.service;

import lombok.RequiredArgsConstructor;
import net.lodgames.shop.category.model.Category;
import net.lodgames.shop.category.param.CategoryParam;
import net.lodgames.shop.category.repository.CategoryQueryRepository;
import net.lodgames.shop.category.repository.CategoryRepository;
import net.lodgames.shop.category.util.CategoryMapper;
import net.lodgames.shop.category.vo.CategoryVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryQueryRepository categoryQueryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryVo> getCategoryList() {
        return categoryQueryRepository.getCategoryList();
    }

    // TODO move to Admin
    @Transactional(rollbackFor = Exception.class)
    public CategoryVo createCategory(CategoryParam categoryParam) {
        Category category = categoryRepository.save(Category.builder()
                .name(categoryParam.getName())
                .description(categoryParam.getDescription())
                .build());
        return categoryMapper.updateCategoryToVo(category);
    }
}
