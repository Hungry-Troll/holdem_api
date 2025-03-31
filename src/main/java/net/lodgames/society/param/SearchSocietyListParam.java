package net.lodgames.society.param;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class SearchSocietyListParam extends PagingParam {
    private String keyword;
}
