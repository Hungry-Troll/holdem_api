package net.lodgames.shop.bundle.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BundleParam {
    @JsonIgnore
    private long userId;
    private Long bundleId;
}
