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
import net.lodgames.user.vo.ProfileVo;
import org.springframework.stereotype.Service;
import net.lodgames.config.error.ErrorCode;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileMapper profileMapper;

    private final ProfileRepository profileRepository;

    // 프로필 조회
    public ProfileVo getProfile(Long profileId) {
        return profileMapper.updateProfileToVo(retrieveProfile(profileId));
    }

    // 프로필 추가
    @Transactional
    public ProfileVo addProfile(ProfileAddParam profileAddParam) {
        Profile profile = profileRepository.save(Profile.builder()
                        .image(profileAddParam.getImage())
                        .userId(profileAddParam.getUserId())
                        .nickname(profileAddParam.getNickname())
                        .uniqueNickname(profileAddParam.getUniqueNickname()).build()
        );
        return profileMapper.updateProfileToVo(profile);
    }

    // 프로필 변경
    public ProfileVo modProfile(ProfileModParam profileModParam) {
        Profile profile = retrieveProfile(profileModParam.getId());
        profileMapper.updateProfileFromParam(profileModParam, profile);
        return profileMapper.updateProfileToVo(profileRepository.save(profile));
    }
    
    // 프로필 가져오기
    protected Profile retrieveProfile(long profileId) {
        return profileRepository.findById(profileId).orElseThrow(
                () -> new RestException(ErrorCode.PROFILE_NOT_EXIST)
        );
    }
}
