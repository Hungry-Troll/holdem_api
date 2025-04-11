package net.lodgames.society.service;

import net.lodgames.society.constants.JoinType;
import net.lodgames.society.constants.SocietyDummy;
import net.lodgames.society.model.Society;

import java.time.LocalDateTime;

public class SocietyServiceDummy {
    public Society makeDummySociety() {
        Society society = Society.builder()
                .name(SocietyDummy.SOCIETY_NAME_1)
                .joinType(JoinType.PERMIT)
                .info(SocietyDummy.SOCIETY_INFO_1)
                .image(SocietyDummy.SOCIETY_IMAGE_1)
                .backImage(SocietyDummy.SOCIETY_BACK_IMAGE_1)
                .tag(SocietyDummy.SOCIETY_TAG_1)
                .build();
        society.setId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID);
        society.setCreatedAt(LocalDateTime.now());
        society.setUpdatedAt(LocalDateTime.now());
        return society;
    }

    public Society makeDummySociety2() {
        Society society = Society.builder()
                .name(SocietyDummy.SOCIETY_NAME_2)
                .joinType(JoinType.FREE)
                .info(SocietyDummy.SOCIETY_INFO_2)
                .image(SocietyDummy.SOCIETY_IMAGE_2)
                .backImage(SocietyDummy.SOCIETY_BACK_IMAGE_2)
                .tag(SocietyDummy.SOCIETY_TAG_2)
                .build();
        society.setId(2L);
        society.setCreatedAt(LocalDateTime.now());
        society.setUpdatedAt(LocalDateTime.now());
        return society;
    }
}
