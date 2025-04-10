package net.lodgames.society.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.society.constants.MemberType;
import net.lodgames.society.constants.WaitType;
import net.lodgames.society.model.SocietyMember;
import net.lodgames.society.model.SocietyMemberWait;
import net.lodgames.society.param.wait.SocietyMemberWaitCancelParam;
import net.lodgames.society.param.wait.SocietyMemberWaitListParam;
import net.lodgames.society.param.wait.SocietyMemberWaitRemoveParam;
import net.lodgames.society.param.wait.SocietyOwnWaitListParam;
import net.lodgames.society.repository.SocietyMemberRepository;
import net.lodgames.society.repository.SocietyMemberWaitQueryRepository;
import net.lodgames.society.repository.SocietyMemberWaitRepository;
import net.lodgames.society.vo.SocietyMemberWaitVo;
import net.lodgames.society.vo.SocietyWaitVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SocietyMemberWaitService {
    private final SocietyMemberRepository societyMemberRepository;
    private final SocietyMemberWaitRepository societyMemberWaitRepository;
    private final SocietyMemberWaitQueryRepository societyMemberWaitQueryRepository;


    // remove society member ( check leader auth)
    @Transactional(rollbackFor = {Exception.class})
    public void removeSocietyMemberWait(SocietyMemberWaitRemoveParam societyMemberWaitRemoveParam) {
        // 모임에 멤버인지 확인
        SocietyMemberWait targetSocietyMemberWait = getSocietyMemberWait(societyMemberWaitRemoveParam.getSocietyId(), societyMemberWaitRemoveParam.getMemberId());
        // 해당 모임에 운영자 이상의 권한이 있는지 확인
        checkSocietyMemberAuth(societyMemberWaitRemoveParam.getSocietyId(), societyMemberWaitRemoveParam.getUserId(), MemberType.OPERATOR);
        // 해당 인원 모임에서 제거
        societyMemberWaitRepository.delete(targetSocietyMemberWait);
    }

    // leave society
    @Transactional(rollbackFor = {Exception.class})
    public void cancelSocietyMemberWait(SocietyMemberWaitCancelParam societyMemberWaitCancelParam) {
        // 모임에 멤버인지 확인
        SocietyMemberWait societyMemberWait = getSocietyMemberWait(societyMemberWaitCancelParam.getSocietyId(), societyMemberWaitCancelParam.getUserId());
        // 해당 모임에서 탈퇴
        societyMemberWaitRepository.delete(societyMemberWait);
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

    // 모임 멤버 대기 취득
    private SocietyMemberWait getSocietyMemberWait(long societyId, long userId) {
        return societyMemberWaitRepository.findBySocietyIdAndUserId(societyId, userId)
                .orElseThrow(() -> new RestException(ErrorCode.MEMBER_WAIT_NOT_EXIST));
    }

    // 모임 가입 대기 / 초대 대기
    public List<SocietyMemberWaitVo> getSocietyMemberWaitList(SocietyMemberWaitListParam societyMemberWaitListParam) {
        // 해당 모임에 운영자 이상의 권한이 있는지 확인
        checkSocietyMemberAuth(societyMemberWaitListParam.getSocietyId(), societyMemberWaitListParam.getUserId(), MemberType.OPERATOR);
        // 해당 모임 멤버 조회
        return societyMemberWaitQueryRepository.selectSocietyMemberWait(societyMemberWaitListParam, societyMemberWaitListParam.of());
    }

    public List<SocietyWaitVo> getSocietyOwnWaitList(SocietyOwnWaitListParam societyOwnWaitListParam) {
        societyOwnWaitListParam.setWaitType(WaitType.APPLY);
        // 해당 모임 멤버 조회
        return societyMemberWaitQueryRepository.selectSocietyOwnWaitList(societyOwnWaitListParam, societyOwnWaitListParam.of());
    }

}