package net.lodgames.profile.service;

import net.lodgames.profile.vo.ProfileVo;
import net.lodgames.user.repository.UserQueryRepository;
import net.lodgames.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.profile.model.Profile;
import net.lodgames.profile.param.ProfileAddParam;
import net.lodgames.profile.param.ProfileModParam;
import net.lodgames.profile.repository.ProfileRepository;
import net.lodgames.profile.util.ProfileMapper;
import org.springframework.stereotype.Service;
import net.lodgames.config.error.ErrorCode;

import java.security.SecureRandom;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;

    // 랜덤 닉네임 생성용
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ALPHABET_LENGTH = 2; // 앞 2글자
    private static final int NUMBER_LENGTH = 7;   // 뒤 7글자
    private static final String SAP = "_";
    private final SecureRandom random = new SecureRandom();

    // 프로필 조회
    @Transactional(rollbackFor = {Exception.class})
    public ProfileVo getProfile(Long userId) {
        return profileMapper.updateProfileToVo(retrieveProfile(userId));
    }

    // 프로필 추가
    @Transactional(rollbackFor = {Exception.class})
    public ProfileVo addProfile(ProfileAddParam profileAddParam) {
        // 닉네임 조건 검사
        checkProfileNickname(profileAddParam.getNickname(),
                profileAddParam.getUserId());
        // 동일한 유저가 이미 프로필을 가지고 있는 경우 확인
        if (profileRepository.existsByUserId(profileAddParam.getUserId())) {
            throw new RestException(ErrorCode.EXIST_PROFILE);
        }
        // 프로필 추가
        Profile profile = saveProfile(
                profileAddParam.getUserId(),
                profileAddParam.getNickname(),
                profileAddParam.getImage(),
                profileAddParam.getBasicImageIdx()
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
        Profile profile = profileRepository.findById(profileId).orElseThrow(() ->
                new RestException(ErrorCode.PROFILE_NOT_EXIST));

        profileRepository.delete(profile);
        return profileMapper.updateProfileToVo(profile);
    }

    // 프로필 가져오기
    @Transactional(rollbackFor = {Exception.class})
    protected Profile retrieveProfile(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    if (!userRepository.existsByUserId(userId)) {
                        throw new RestException(ErrorCode.USER_NOT_EXIST);
                    }
                    return addBasicProfile(userId, createRandomNickName());
                });
    }

    public Profile addBasicProfile(long userId, String nickname) {
        // 기본 프로필 추가
        return saveProfile(userId, nickname, "", 0);
    }

    // 프로필 저장
    private Profile saveProfile(Long userId, String nickname, String image, Integer basicImageIdx) {
        return profileRepository.save(Profile.builder()
                .userId(userId)
                .image(image)
                .basicImageIdx(basicImageIdx)
                .nickname(nickname)
                .build());
    }


    // 닉네임 조건 검사
    private void checkProfileNickname(String nickname, Long userId) {
        if (userQueryRepository.searchUserNickname(nickname)) {
            checkProfileNicknameIsMe(nickname, userId); // 동일한 닉네임의 경우 자신의 이름과 같은지 검사
        }
        if (nickname.trim().length() < 2) {
            throw new RestException(ErrorCode.NICKNAME_TOO_SHORT);// 닉네임이 너무 짧음
        }
    }

    // 동일한 닉네임의 경우 자신의 이름과 같은지 검사
    private void checkProfileNicknameIsMe(String nickname, Long userId) {
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() ->
                new RestException(ErrorCode.PROFILE_NOT_EXIST));
        // 찾은 프로필 이름하고 닉네임이 다를 경우 (이름이 같으면 내 이름이니까 중복이 아님)
        if (!nickname.equals(profile.getNickname())) {
            throw new RestException(ErrorCode.EXIST_NICKNAME);// 동일한 닉네임 있음
        }
    }

    //랜덤 닉네임 생성
    public String createRandomNickName() {
        StringBuilder nickname = new StringBuilder();

        // 대문자 알파벳 2글자 추가
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            char letter = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
            nickname.append(letter);
        }

        nickname.append(SAP);

        // 숫자 7글자 추가
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            int digit = random.nextInt(10);
            nickname.append(digit);
        }

        return nickname.toString();
    }
}
