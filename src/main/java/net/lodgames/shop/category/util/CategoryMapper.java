package net.lodgames.shop.category.util;

import net.lodgames.shop.category.model.Category;
import net.lodgames.shop.category.vo.CategoryVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CategoryVo updateCategoryToVo(Category category);
}
