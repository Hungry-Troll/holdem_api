package net.lodgames.version.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LatestAppVersionVo {
    private String forceVersion;
    private String induceVersion;
    private String bundleVersion;
}
