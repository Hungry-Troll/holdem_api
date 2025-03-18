package net.lodgames.character.service;

import lombok.extern.slf4j.Slf4j;
import net.lodgames.character.param.UserCharacterAddParam;
import net.lodgames.character.param.UserCharacterModParam;
import net.lodgames.character.param.UserCharactersGetParam;
import net.lodgames.character.vo.UserCharacterVo;
import net.lodgames.character.vo.UserCharactersVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class UserCharacterServiceTest {

    @Autowired
    private UserCharacterService userCharacterService;

    // 유저 캐릭터 등록
    @Test
    void addUserCharacter() {
        UserCharacterAddParam userCharacterAddParam = new UserCharacterAddParam();
        userCharacterAddParam.setUserId(1L);
        userCharacterAddParam.setCharacterId(1L);
        userCharacterAddParam.setCustomiseId(1L);
        userCharacterAddParam.setLevel(1);
        userCharacterAddParam.setGrade(1);
        userCharacterAddParam.setStatusIndex(1);

        UserCharacterAddParam userCharacterAddParam2 = new UserCharacterAddParam();
        userCharacterAddParam2.setUserId(2L);
        userCharacterAddParam2.setCharacterId(2L);
        userCharacterAddParam2.setCustomiseId(2L);
        userCharacterAddParam2.setLevel(2);
        userCharacterAddParam2.setGrade(2);
        userCharacterAddParam2.setStatusIndex(2);
        userCharacterService.addUserCharacter(userCharacterAddParam);
        userCharacterService.addUserCharacter(userCharacterAddParam2);
    }

    // 유저 캐릭터 조회
    @Test
    void getUserCharacter() {
        UserCharacterVo vo = userCharacterService.getUserCharacter(1L, 1L);
        assertThat(vo).isNotNull();
        assertThat(vo.getUserId()).isEqualTo(1L);
        assertThat(vo.getCharacterId()).isEqualTo(1L);
        assertThat(vo.getCustomiseId()).isEqualTo(1L);
        assertThat(vo.getLevel()).isEqualTo(1);
        assertThat(vo.getGrade()).isEqualTo(1);
        assertThat(vo.getStatusIndex()).isEqualTo(1);

        UserCharacterVo vo2 = userCharacterService.getUserCharacter(2L, 2L);
        assertThat(vo).isNotNull();
        assertThat(vo2.getUserId()).isEqualTo(2L);
        assertThat(vo2.getCharacterId()).isEqualTo(2L);
        assertThat(vo2.getCustomiseId()).isEqualTo(2L);
        assertThat(vo2.getLevel()).isEqualTo(2);
        assertThat(vo2.getGrade()).isEqualTo(2);
        assertThat(vo2.getStatusIndex()).isEqualTo(2);
    }

    // 유저 캐릭터 전체 조회
    @Test
    void getUserCharacters() {
         UserCharactersGetParam userCharactersGetParam = new UserCharactersGetParam();
         userCharactersGetParam.setUserId(1L);
         List<UserCharactersVo> userCharacters = userCharacterService.getUserCharacters(userCharactersGetParam);

         for (UserCharactersVo userCharacter : userCharacters) {
             log.info("userCharacter.getId() : {}", userCharacter.getId());
             log.info("userCharacter.getUserId() : {}", userCharacter.getUserId());
             log.info("userCharacter.getCharacterId() : {}", userCharacter.getCharacterId());
             log.info("userCharacter.getCustomiseId() : {}", userCharacter.getCustomiseId());
             log.info("userCharacter.getLevel() : {}", userCharacter.getLevel());
             log.info("userCharacter.getGrade() : {}", userCharacter.getGrade());
             log.info("userCharacter.getStatusIndex() : {}", userCharacter.getStatusIndex());
         }
    }

    // 유저 캐릭터 삭제
    @Test
    void deleteUserCharacter() {
        userCharacterService.deleteUserCharacter(1L, 1L);
        userCharacterService.deleteUserCharacter(2L, 2L);
    }

    // 유저 캐릭터 수정
    @Test
    void modUserCharacter() {
        UserCharacterModParam userCharacterModParam = new UserCharacterModParam();
        userCharacterModParam.setId(3L);
        userCharacterModParam.setCharacterId(2L);
        userCharacterModParam.setCustomiseId(2L);
        userCharacterModParam.setUserId(1L);
        userCharacterModParam.setLevel(2);
        userCharacterModParam.setGrade(2);
        userCharacterModParam.setStatusIndex(3);
        userCharacterService.modUserCharacter(userCharacterModParam);
    }

    // 유저 캐릭터 레벨업
    @Test
    void levelUpUserCharacter() {
        userCharacterService.levelUpUserCharacter(3L, 1L);
    }

    // 유저 캐릭터 등급업
    @Test
    void gradeUpUserCharacter() {
        userCharacterService.gradeUpUserCharacter(3L, 1L);
    }
}