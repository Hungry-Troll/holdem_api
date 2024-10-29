package net.lodgames.user.service;

import jakarta.transaction.Transactional;
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

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    @Transactional
    // 프로필 조회
    public ProfileVo getProfile(Long userId) {
        return profileMapper.updateProfileToVo(retrieveProfile(userId));
    }

    // 프로필 추가 (조회 후 없으면 추가)
    @Transactional
    public ProfileVo addProfile(ProfileAddParam profileAddParam) {
        Profile profile = profileRepository.save(Profile.builder()
                .image(profileAddParam.getImage())
                .userId(profileAddParam.getUserId())
                .nickname(profileAddParam.getNickname()).build()
        );
        return profileMapper.updateProfileToVo(profile);
    }

    // 프로필 변경
    @Transactional
    public ProfileVo modProfile(ProfileModParam profileModParam) {
        Profile profile = retrieveProfile(profileModParam.getUserId());
        profileMapper.updateProfileFromModParam(profileModParam, profile);
        return profileMapper.updateProfileToVo(profileRepository.save(profile));
    }

    // 프로필 삭제 (로컬 테스트용)
    @Transactional
    public ProfileVo deleteProfile(Long profileId) {
        Profile findProfile = profileRepository.findById(profileId).orElseThrow(()->
                new RestException(ErrorCode.PROFILE_NOT_EXIST));

        profileRepository.delete(findProfile);
        return profileMapper.updateProfileToVo(findProfile);
    }

    // 프로필 가져오기
    @Transactional
    protected Profile retrieveProfile(long userId) {
        return profileRepository.findByUserId(userId).orElseThrow(
                () -> new RestException(ErrorCode.USER_NOT_EXIST)
        );
    }
}
