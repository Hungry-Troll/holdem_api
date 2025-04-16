package net.lodgames.character.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.character.param.UserCharactersGetParam;
import net.lodgames.character.repository.UserCharacterQueryRepository;
import net.lodgames.character.vo.UserCharactersVo;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.character.model.UserCharacter;
import net.lodgames.character.param.UserCharacterAddParam;
import net.lodgames.character.param.UserCharacterModParam;
import net.lodgames.character.repository.UserCharacterRepository;
import net.lodgames.character.util.UserCharacterMapper;
import net.lodgames.character.vo.UserCharacterVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserCharacterService {
    private final UserCharacterQueryRepository userCharacterQueryRepository;
    private final UserCharacterRepository userCharacterRepository;
    private final UserCharacterMapper userCharacterMapper;

    // 유저 캐릭터 등록
    @Transactional(rollbackFor = {Exception.class})
    public void addUserCharacter(UserCharacterAddParam userCharacterAddParam) {
        UserCharacter userCharacter = userCharacterRepository.save(UserCharacter.builder()
                .userId(userCharacterAddParam.getUserId())
                .characterId(userCharacterAddParam.getCharacterId())
                .customiseId(userCharacterAddParam.getCustomiseId())
                .level(userCharacterAddParam.getLevel())
                .grade(userCharacterAddParam.getGrade())
                .statusIndex(userCharacterAddParam.getStatusIndex())
                .build());
        userCharacterRepository.save(userCharacter);
    }

    // 유저 캐릭터 조회
    @Transactional(readOnly = true)
    public UserCharacterVo getUserCharacter(Long id, Long userId) {
        UserCharacter userCharacter = retrieveUserCharacter(id, userId);
        return userCharacterMapper.updateUserCharacterToVo(userCharacter);
    }

    // 유저 캐릭터 전체 조회
    @Transactional(readOnly = true)
    public List<UserCharactersVo> getUserCharacters(UserCharactersGetParam userCharactersGetParam) {
        return userCharacterQueryRepository.getUserCharacters(userCharactersGetParam, userCharactersGetParam.of());
    }

    // 유저 캐릭터 삭제
    @Transactional(rollbackFor = {Exception.class})
    public void deleteUserCharacter(Long id, Long userId) {
        UserCharacter userCharacter = retrieveUserCharacter(id, userId);
        userCharacterRepository.delete(userCharacter);
    }

    // 유저 캐릭터 수정
    @Transactional(rollbackFor = {Exception.class})
    public UserCharacterVo modUserCharacter(UserCharacterModParam userCharacterModParam) {
        Long id = userCharacterModParam.getId();
        Long userId = userCharacterModParam.getUserId();
        UserCharacter userCharacter = retrieveUserCharacter(id, userId);
        userCharacterMapper.updateUserCharacterFromModParam(userCharacterModParam, userCharacter);
        userCharacterRepository.save(userCharacter);
        return userCharacterMapper.updateUserCharacterToVo(userCharacter);
    }

    // 유저 캐릭터 레벨업
    @Transactional(rollbackFor = {Exception.class})
    public void levelUpUserCharacter(Long id, Long userId) {
        int levelUp = 1;
        // 유저 캐릭터가 존재하는지 확인
        UserCharacter userCharacter = retrieveUserCharacter(id, userId);
        // 레벨업
        // TODO 최대 레벨 확인해야 됨 (기획)
        userCharacter.setLevel(userCharacter.getLevel() + levelUp);
        // 저장
        userCharacterRepository.save(userCharacter);
    }

    // 유저 캐릭터 등급업
    @Transactional(rollbackFor = {Exception.class})
    public void gradeUpUserCharacter(Long id, Long userId) {
        int gradeUp = 1;
        // 유저 캐릭터가 존재하는지 확인
        UserCharacter userCharacter = retrieveUserCharacter(id, userId);
        // 등급업
        // TODO 최대 등급 확인해야 됨 (기획)
        userCharacter.setGrade(userCharacter.getGrade() + gradeUp);
        // 저장
        userCharacterRepository.save(userCharacter);
    }

    private UserCharacter retrieveUserCharacter(Long id, Long userId) {
        return userCharacterRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_CHARACTER_NOT_EXIST));
    }
}
