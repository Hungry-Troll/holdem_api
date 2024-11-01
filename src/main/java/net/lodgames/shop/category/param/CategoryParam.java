package net.lodgames.shop.category.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryParam {
    private String name;
    private String description;
}
