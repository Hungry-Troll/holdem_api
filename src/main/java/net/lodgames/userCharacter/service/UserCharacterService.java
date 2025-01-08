package net.lodgames.userCharacter.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.userCharacter.model.UserCharacter;
import net.lodgames.userCharacter.param.UserCharacterAddParam;
import net.lodgames.userCharacter.param.UserCharacterModParam;
import net.lodgames.userCharacter.repository.UserCharacterRepository;
import net.lodgames.userCharacter.util.UserCharacterMapper;
import net.lodgames.userCharacter.vo.UserCharacterGetVo;
import net.lodgames.userCharacter.vo.UserCharacterModVo;
import net.lodgames.userCharacter.vo.UserCharactersGetVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserCharacterService {

    private final UserCharacterRepository userCharacterRepository;
    private final UserCharacterMapper userCharacterMapper;

    // 유저 캐릭터 등록
    @Transactional(rollbackFor = {Exception.class})
    public void addUserCharacter(UserCharacterAddParam userCharacterAddParam) {
        userCharacterRepository.save(
                userCharacterMapper.updateAddParamToUserCharacter(userCharacterAddParam));
    }

    // 유저 캐릭터 조회
    @Transactional(readOnly = true)
    public UserCharacterGetVo getUserCharacter(Long id, Long userId) {
        UserCharacter findUserCharacter =  userCharacterRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_CHARACTER_NOT_EXIST));
        return userCharacterMapper.userCharacterToGetVo(findUserCharacter);
    }

    // 유저 캐릭터 전체 조회
    @Transactional(readOnly = true)
    public List<UserCharactersGetVo> getUserCharacters(Long userId) {
        return userCharacterMapper.userCharactersToGetVo(
                userCharacterRepository.findByUserId(userId));
    }

    // 유저 캐릭터 삭제
    @Transactional(rollbackFor = {Exception.class})
    public void deleteUserCharacter(Long id, Long userId) {
        UserCharacter userCharacter = userCharacterRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_CHARACTER_NOT_EXIST));
        userCharacterRepository.delete(userCharacter);
    }

    // 유저 캐릭터 수정
    @Transactional(rollbackFor = {Exception.class})
    public UserCharacterModVo modUserCharacter(UserCharacterModParam userCharacterModParam) {
        // 유저 캐릭터가 존재하는지 확인
        UserCharacter findUserCharacter = userCharacterRepository.findByIdAndUserId(
                userCharacterModParam.getId(),
                userCharacterModParam.getUserId())
                .orElseThrow(() -> new RestException(ErrorCode.USER_CHARACTER_NOT_EXIST));
        // 수정된 정보로 업데이트
        findUserCharacter.setCharacterId(userCharacterModParam.getCharacterId());
        findUserCharacter.setCustomiseId(userCharacterModParam.getCustomiseId());
        findUserCharacter.setLevel(userCharacterModParam.getLevel());
        findUserCharacter.setGrade(userCharacterModParam.getGrade());
        findUserCharacter.setStatusIndex(userCharacterModParam.getStatusIndex());
        // 저장 후 반환
        return userCharacterMapper.userCharacterToModVo(
                userCharacterRepository.save(findUserCharacter));

    }
}
