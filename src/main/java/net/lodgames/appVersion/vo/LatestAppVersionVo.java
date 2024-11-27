package net.lodgames.appVersion.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class LatestAppVersionVo {
    private String forceVersion;
    private String induceVersion;
    private String bundleVersion;
}
