package net.lodgames.user.service;

import net.lodgames.user.repository.UserQueryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.user.model.Profile;
import net.lodgames.user.param.ProfileAddParam;
import net.lodgames.user.param.ProfileModParam;
import net.lodgames.user.repository.ProfileRepository;
import net.lodgames.user.util.ProfileMapper;
import net.lodgames.user.vo.*;
import org.springframework.stereotype.Service;
import net.lodgames.config.error.ErrorCode;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;
    private final UserQueryRepository userQueryRepository;

    // 프로필 조회
    @Transactional(readOnly = true)
    public ProfileVo getProfile(Long userId) {
        return profileMapper.updateProfileToVo(retrieveProfile(userId));
    }

    // 프로필 추가 (동일 유저가 중복으로 프로필 추가 안한다는 가정)
    @Transactional(rollbackFor = {Exception.class})
    public ProfileVo addProfile(ProfileAddParam profileAddParam) {
        // 닉네임 조건 검사
        checkProfileNickname(profileAddParam.getNickname(),
                             profileAddParam.getUserId());
        Profile profile = profileRepository.save(Profile.builder()
                .image(profileAddParam.getImage())
                .basicImageIdx(profileAddParam.getBasicImageIdx())
                .userId(profileAddParam.getUserId())
                .nickname(profileAddParam.getNickname()).build()
        );
        return profileMapper.updateProfileToVo(profile);
    }

    // 프로필 변경
    @Transactional(rollbackFor = {Exception.class})
    public ProfileVo modProfile(ProfileModParam profileModParam) {
        // 닉네임 조건 검사
        checkProfileNickname(profileModParam.getNickname(),
                             profileModParam.getUserId());
        Profile profile = retrieveProfile(profileModParam.getUserId());
        profileMapper.updateProfileFromModParam(profileModParam, profile);
        return profileMapper.updateProfileToVo(profileRepository.save(profile));
    }

    // 프로필 삭제 (로컬 테스트용)
    @Transactional(rollbackFor = {Exception.class})
    public ProfileVo deleteProfile(Long profileId) {
        Profile findProfile = profileRepository.findById(profileId).orElseThrow(()->
                new RestException(ErrorCode.PROFILE_NOT_EXIST));

        profileRepository.delete(findProfile);
        return profileMapper.updateProfileToVo(findProfile);
    }

    // 프로필 가져오기
    @Transactional(readOnly = true)
    protected Profile retrieveProfile(long userId) {
        return profileRepository.findByUserId(userId).orElseThrow(() ->
                new RestException(ErrorCode.PROFILE_NOT_EXIST)
        );
    }

    // 닉네임 조건 검사
    private void checkProfileNickname(String nickname, Long userId) {
        if(userQueryRepository.searchUserNickname(nickname)) {
            checkProfileNicknameIsMe(nickname, userId); // 동일한 닉네임의 경우 자신의 이름과 같은지 검사
        }
        if(nickname.trim().length() < 2) {
            throw new RestException(ErrorCode.NICKNAME_TOO_SHORT);// 닉네임이 너무 짧음
        }
    }

    // 동일한 닉네임의 경우 자신의 이름과 같은지 검사
    private void checkProfileNicknameIsMe(String nickname, Long userId) {
        Profile findProfile = profileRepository.findByUserId(userId).orElseThrow(()->
                new RestException(ErrorCode.PROFILE_NOT_EXIST));
        // 찾은 프로필 이름하고 닉네임이 다를 경우 (이름이 같으면 내 이름이니까 중복이 아님)
        if(!nickname.equals(findProfile.getNickname())) {
            throw new RestException(ErrorCode.EXIST_NICKNAME);// 동일한 닉네임 있음
        }
    }
}
