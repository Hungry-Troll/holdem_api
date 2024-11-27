package net.lodgames.appVersion.constants;

import lombok.Getter;

@Getter
public enum PublishStatus {
    NOT_PUBLISH("notPublish"),
    PUBLISH("publish"),
    ;
    private String typeName;
    PublishStatus(String typeName) {
        this.typeName = typeName;
    }
}
