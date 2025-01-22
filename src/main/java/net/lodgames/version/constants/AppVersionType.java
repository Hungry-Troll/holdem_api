package net.lodgames.version.constants;

import lombok.Getter;

@Getter
public enum AppVersionType {
    FORCE("force"),   //강제 업데이트
    INDUCE("induce"), //권장 업데이트
    BUNDLE("bundle"), // 번들 업데이트
    ;
    private String type;

    AppVersionType(String type) {
        this.type = type;
    }
}
