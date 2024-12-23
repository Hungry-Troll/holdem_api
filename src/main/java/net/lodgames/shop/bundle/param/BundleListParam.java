package net.lodgames.shop.bundle.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;
import net.lodgames.shop.bundle.constants.BundleSearchType;

@Getter
@Setter
public class BundleListParam extends PagingParam {
    @JsonIgnore
    private long userId;
    private BundleSearchType searchType;
    private String searchValue;
}
