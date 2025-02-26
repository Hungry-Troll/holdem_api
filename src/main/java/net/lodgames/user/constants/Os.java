package net.lodgames.user.constants;


import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Os {
    WEB(0), ANDROID(1), IOS(2), OTHER(3);

    Os(int typeNum) {
        this.typeNum = typeNum;
    }

    private final int typeNum;

    public static Os valueOfTypeNum(int typeNum) {
        return Arrays.stream(values())
                .filter(os -> os.typeNum == typeNum)
                .findFirst()
                .orElse(OTHER);
    }
}
