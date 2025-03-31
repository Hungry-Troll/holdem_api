package net.lodgames.society.service;

import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.society.constants.JoinType;
import net.lodgames.society.constants.MemberType;
import net.lodgames.society.model.Society;
import net.lodgames.society.model.SocietyMember;
import net.lodgames.society.model.SocietyMemberWait;
import net.lodgames.society.param.*;
import net.lodgames.society.repository.SocietyMemberRepository;
import net.lodgames.society.repository.SocietyMemberWaitRepository;
import net.lodgames.society.repository.SocietyQueryRepository;
import net.lodgames.society.repository.SocietyRepository;
import net.lodgames.society.util.SocietyMapper;
import net.lodgames.society.vo.SocietyInfoVo;
import net.lodgames.society.vo.SocietyVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SocietyService {
    private final SocietyQueryRepository societyQueryRepository;
    private final SocietyRepository societyRepository;
    private final SocietyMemberRepository societyMemberRepository;
    private final SocietyMemberWaitRepository societyMemberWaitRepository;
    private final SocietyMapper societyMapper;
    private final int SOCIETY_LEAD_MAX_NUM = 5;

    // society list
    public List<SocietyVo> getSocietyList(SocietyListParam societyListParam) {
        List<SocietyVo> societyVos = societyQueryRepository.findSocietyByUserIdAndMemberType(societyListParam, societyListParam.of());
        for (SocietyVo societyVo : societyVos) {
            societyVo.setTags(getTagList(societyVo.getTag()));
        }
        return societyVos;
    }

    // add society
    @Transactional(rollbackFor = {Exception.class})
    public SocietyInfoVo addSociety(SocietyParam societyParam) {
        if (StringUtil.isNullOrEmpty(societyParam.getName())
                || societyParam.getJoinType() == null
                || societyParam.getInfo() == null
                || (societyParam.getJoinType() == JoinType.LOCK && societyParam.getPasscode() == null)
        ) {
            throw new RestException(ErrorCode.FAIL_ADD_SOCIETY_INVALID_PARAMETER);
        }

        // Check the number of society as a leader
        int leaderSocietyCount = societyMemberRepository.countByUserIdAndMemberType(societyParam.getUserId(), MemberType.LEADER);
        if (leaderSocietyCount >= SOCIETY_LEAD_MAX_NUM) { // 5개 이상일 경우
            throw new RestException(ErrorCode.FAIL_ADD_SOCIETY_LEAD_LIMIT);
        }

        // tag Array 를 String 으로 변환
        String tag = tagsToString(societyParam.getTags());
        Society.SocietyBuilder societyBuilder = Society.builder()
                .name(societyParam.getName())               // 모임 이름
                .joinType(societyParam.getJoinType())       // 모임 가입 유형
                .image(societyParam.getImage())             // 대표 이미지
                .backImage(societyParam.getBackImage())     // 배경 이미지
                .info(societyParam.getInfo())               // 모임정보
                .tag(tag);                                  // 태그정보

        // 가입 유형이 LOCK 일 경우
        if(societyParam.getJoinType() == JoinType.LOCK) {
            societyBuilder.passcode(societyParam.getPasscode()); // 비밀번호
        }

        // 모임 추가
        Society society = societyRepository.save(societyBuilder.build());

        // 멤버 추가 ( 모임장 )
        long societyId = society.getId();
        societyMemberRepository.save(SocietyMember.builder()
                .societyId(societyId)              // 생성된 모임 고유번호
                .userId(societyParam.getUserId())  // 추가될 멤버 고유번호
                .memberType(MemberType.LEADER)     // 멤버 타입 리더
                .build());
        SocietyInfoVo societyInfoVo = societyMapper.updateSocietyToInfoVo(society);
        societyInfoVo.setTags(getTagList(society.getTag()));
        societyInfoVo.setMemberCount(1L);
        return societyInfoVo;
    }

    private String tagsToString(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        return tags.stream()
                .map(tag -> tag.replaceAll(" ", "").toLowerCase().trim())
                .filter(tag -> !tag.isBlank())
                .reduce((tag1, tag2) -> tag1 + "," + tag2)
                .orElse(null);
    }

    public List<String> getTagList(String tag) {
        return tag != null && !tag.trim().isEmpty()
                ? Arrays.asList(tag.split(","))
                : null;
    }

    // mod society (name)
    @Transactional(rollbackFor = {Exception.class})
    public void modSociety(SocietyModParam societyModParam) {
        // 해당 모임 조회
        Society society = getSocietyEntity(societyModParam.getSocietyId());
        // 해당 모임에 리더 이상의 권한이 있는지 확인
        checkSocietyMemberAuth(societyModParam.getSocietyId(), societyModParam.getUserId(), MemberType.LEADER);
        // tag String 취득
        String tag = tagsToString(societyModParam.getTags());
        // 태그 변경이 있을 경우
        if (!StringUtil.isNullOrEmpty(tag)) {
            society.setTag(tag);
        }
        // 모임 정보 수정
        societyMapper.updateSocietyFromParam(societyModParam, society);
        // 가입 유형 변경
        changeJoinType(society, societyModParam.getJoinType(), societyModParam.getPasscode());

        try {
            societyRepository.save(society);
        } catch (Exception e) {
            log.info("fail unfollow : {}", e.getCause().getCause().toString());
            throw new RestException(ErrorCode.FAIL_MOD_SOCIETY);
        }
    }

    // 가입 유형 변경
    private void changeJoinType(Society society, JoinType newJoinType, String passcode) {
        JoinType oldJoinType = society.getJoinType();
        // 가입 유형이 변경되지 않을 경우
        if (oldJoinType == newJoinType) {
            // 가입 유형이 LOCK 일 경우
            if (newJoinType == JoinType.LOCK && society.getPasscode().equals(passcode)) {
                throw new RestException(ErrorCode.FAIL_MOD_SOCIETY);
            }
            return;
            // 가입 유형이 변경될 경우
        } else {
            // 가입 유형이 LOCK 일 경우
            if (newJoinType == JoinType.LOCK) {
                if (passcode == null) {
                    throw new RestException(ErrorCode.FAIL_MOD_SOCIETY);
                }
            } else {
                passcode = null;
            }
        }
        society.setJoinType(newJoinType);
        society.setPasscode(passcode);
    }

    @Transactional(readOnly = true)
    public SocietyInfoVo getSocietyInfo(SocietyInfoParam societyInfoParam) {
        SocietyInfoVo societyInfoVo = societyQueryRepository.findSocietyBySocietyId(societyInfoParam.getSocietyId());
        if (societyInfoVo == null) {
            throw new RestException(ErrorCode.SOCIETY_NOT_EXIST);
        }
        societyInfoVo.setTags(getTagList(societyInfoVo.getTag()));
        // 해당 모임 조회
        return societyInfoVo;
    }

    // remove society
    @Transactional(rollbackFor = {Exception.class})
    public void removeSociety(SocietyDeleteParam societyDeleteParam) {
        long userId = societyDeleteParam.getUserId();
        long societyId = societyDeleteParam.getSocietyId();
        // 해당 모임 존재 여부 확인
        Society society = getSocietyEntity(societyId);
        // 해당 모임에 리더 이상의 권한이 있는지 확인
        checkSocietyMemberAuth(societyId, userId, MemberType.LEADER);
        // 멤버 전부 삭제
        List<SocietyMember> societyMemberEntities = societyMemberRepository.removeBySocietyId(societyId);
        // TODO 이후 각 상황에 대한 알림 제공
        for (SocietyMember societyMember : societyMemberEntities) {
            log.debug("deleted member : {}", societyMember.getUserId());
        }

        // 멤버 대기 전부 삭제
        List<SocietyMemberWait> societyMemberWaitEntities = societyMemberWaitRepository.removeBySocietyId(societyId);
        // TODO 이후 각 상황에 대한 알림 제공
        for (SocietyMemberWait societyMemberWait : societyMemberWaitEntities) {
            log.debug("deleted member wait: {}", societyMemberWait.getUserId());
        }
        // 해당 모임 삭제
        societyRepository.delete(society);
    }

    // 모임 취득
    private Society getSocietyEntity(long societyId) {
        return societyRepository.findById(societyId).
                orElseThrow(() -> new RestException(ErrorCode.NOT_EXIST_SOCIETY));
    }

    // 해당 모임에 필요한 권한(MemberType) 이상의 권한이 있는지 확인
    private void checkSocietyMemberAuth(long societyId, long userId, MemberType memberType) {
        SocietyMember societyMember = getSocietyMember(societyId, userId);
        long type_num = societyMember.getMemberType().getTypeNum();
        if (type_num < memberType.getTypeNum()) {
            // No auth
            throw new RestException(ErrorCode.NO_AUTH_SOCIETY_MEMBER);
        }
    }

    // 모임 멤버 취득
    private SocietyMember getSocietyMember(long societyId, long userId) {
        return societyMemberRepository.findBySocietyIdAndUserId(societyId, userId)
                .orElseThrow(() -> new RestException(ErrorCode.NOT_EXIST_SOCIETY_MEMBER));
    }


}