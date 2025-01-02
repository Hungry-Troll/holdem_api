package net.lodgames.dictionary.userCharacter.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.dictionary.userCharacter.model.UserCharacter;
import net.lodgames.dictionary.userCharacter.param.UserCharacterAddParam;
import net.lodgames.dictionary.userCharacter.param.UserCharacterUpdateParam;
import net.lodgames.dictionary.userCharacter.repository.UserCharacterRepository;
import net.lodgames.dictionary.userCharacter.util.UserCharacterMapper;
import net.lodgames.dictionary.userCharacter.vo.UserCharacterGetVo;
import net.lodgames.dictionary.userCharacter.vo.UserCharacterUpdateVo;
import net.lodgames.dictionary.userCharacter.vo.UserCharactersGetVo;
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
    public UserCharacterUpdateVo updateUserCharacter(UserCharacterUpdateParam userCharacterUpdateParam) {
        // 유저 캐릭터가 존재하는지 확인
        UserCharacter findUserCharacter = userCharacterRepository.findByIdAndUserId(
                userCharacterUpdateParam.getId(),
                userCharacterUpdateParam.getUserId())
                .orElseThrow(() -> new RestException(ErrorCode.USER_CHARACTER_NOT_EXIST));
        // 수정된 정보로 업데이트
        findUserCharacter.setCharacterId(userCharacterUpdateParam.getCharacterId());
        findUserCharacter.setCustomiseId(userCharacterUpdateParam.getCustomiseId());
        findUserCharacter.setLevel(userCharacterUpdateParam.getLevel());
        findUserCharacter.setGrade(userCharacterUpdateParam.getGrade());
        findUserCharacter.setStatusIndex(userCharacterUpdateParam.getStatusIndex());
        // 저장 후 반환
        return userCharacterMapper.userCharacterToUpdateVo(
                userCharacterRepository.save(findUserCharacter));

    }
}
