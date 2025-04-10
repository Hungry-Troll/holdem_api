package net.lodgames.society.service;

import com.querydsl.core.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.society.constants.JoinType;
import net.lodgames.society.constants.MemberType;
import net.lodgames.society.constants.WaitType;
import net.lodgames.society.model.Society;
import net.lodgames.society.model.SocietyMember;
import net.lodgames.society.model.SocietyMemberWait;
import net.lodgames.society.param.member.*;
import net.lodgames.society.repository.*;
import net.lodgames.society.vo.SocietyLeaderVo;
import net.lodgames.society.vo.SocietyMemberVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SocietyMemberService {
    private final SocietyQueryRepository societyQueryRepository;
    private final SocietyRepository societyRepository;
    private final SocietyMemberRepository societyMemberRepository;
    private final SocietyMemberQueryRepository societyMemberQueryRepository;
    private final SocietyMemberWaitRepository societyMemberWaitRepository;

    // society member list
    public List<SocietyMemberVo> getSocietyMemberList(SocietyMemberListParam societyMemberListParam) {
        // 해당 모임 존재 여부 확인
        Society society = getSocietyEntity(societyMemberListParam.getSocietyId());
        // 해당 모임 멤버 조회
        return societyMemberQueryRepository.selectSocietyMember(societyMemberListParam, societyMemberListParam.of());
    }

    // add society member ( join member )
    @Transactional(rollbackFor = {Exception.class})
    public void joinSocietyMember(SocietyMemberParam societyMemberParam) {
        long societyId = societyMemberParam.getSocietyId();
        long userId = societyMemberParam.getUserId();
        // 모임 가입 형태, 리더 고유번호
        SocietyLeaderVo societyLeaderVo = societyQueryRepository.selectSocietyAndSocietyLeader(societyId);
        if(societyLeaderVo == null) {
            throw new RestException(ErrorCode.NOT_EXIST_SOCIETY);
        }
        JoinType joinType = societyLeaderVo.getJoinType();
        // long leaderIdx = societyLeaderVo.getUserId();
        // check already member
        if (societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(societyId, userId)) {
            throw new RestException(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER);
        }

        if (joinType == JoinType.PERMIT) {
            if (societyMemberWaitRepository.existsSocietyMemberWaitBySocietyIdAndUserId(societyId, userId)) {
                throw new RestException(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER_WAIT);
            }
            // 모임 멤버 추가
            societyMemberWaitRepository.save(SocietyMemberWait.builder()
                    .userId(userId)           // 추가될 유저 고유아이디
                    .societyId(societyId)     // 추가될 모임 고유아이디
                    .waitType(WaitType.APPLY) // 추가될 멤버 대기 상태 (신청)
                    .build());
        } else {
            // 비번 가입시 비번 확인
            if (joinType == JoinType.LOCK) {
                checkPassword(societyMemberParam.getPasscode(), societyLeaderVo.getPasscode());
            }
            // 모임 멤버 추가
            societyMemberRepository.save(SocietyMember.builder()
                    .userId(userId)                // 추가될 멤버 고유번호
                    .societyId(societyId)          // 추가될 모임 고유번호
                    .memberType(MemberType.NORMAL) // 추가될 멤버 권한 (일반)
                    .build());
        }
    }

    // add society member ( join member )
    @Transactional(rollbackFor = {Exception.class})
    public void inviteSocietyMember(SocietyMemberInviteParam societyMemberInviteParam) {
        long societyId = societyMemberInviteParam.getSocietyId();
        long userId = societyMemberInviteParam.getUserId();
        long memberId = societyMemberInviteParam.getMemberId();
        // 초대하려는 유저가 해당 모임 가입자인지 확인 
        if (!societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(societyId, userId)) {
            throw new RestException(ErrorCode.NO_AUTH_SOCIETY_MEMBER); // 해당 모임 멤버만 초대 가능
        }

        // check already member
        if (societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(societyId, memberId)) {
            throw new RestException(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER);
        }
        // 모임 대기 멤버 목록 취득
        SocietyMemberWait societyMemberWait = getSocietyMemberWait(societyId, memberId);
        // 모임 대기 멤버 목록 삭제
        if(societyMemberWait != null) {
            if(societyMemberWait.getWaitType() == WaitType.APPLY) {
                societyMemberWaitRepository.delete(societyMemberWait);
                // 모임 멤버 추가
                societyMemberRepository.save(SocietyMember.builder()
                        .userId(memberId)              // 추가될 멤버 고유번호
                        .societyId(societyId)          // 추가될 모임 고유번호
                        .memberType(MemberType.NORMAL) // 추가될 멤버 권한 (일반)
                        .build());
            } else if (societyMemberWait.getWaitType() == WaitType.INVITE) {
                throw new RestException(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER_WAIT);
            }
        } else {
            // 모임 멤버 추가
            societyMemberWaitRepository.save(SocietyMemberWait.builder()
                    .userId(memberId)          // 추가될 유저 고유아이디
                    .societyId(societyId)      // 추가될 모임 고유아이디
                    .waitType(WaitType.INVITE) // 추가될 멤버 대기 상태 (신청)
                    .build());
        }
    }

    private void checkPassword(String inputPass, String pass) throws RestException {
        if (StringUtils.isNullOrEmpty(inputPass) || !inputPass.equals(pass)) {
            throw new RestException(ErrorCode.FAIL_ADD_MEMBER_WRONG_PASSWORD);
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    // allow society member
    public void allowSocietyMember(SocietyMemberAllowParam societyMemberAllowParam) {
        long societyId = societyMemberAllowParam.getSocietyId(); // 해당 모임 고유번호
        long userId = societyMemberAllowParam.getUserId();       //
        long memberId = societyMemberAllowParam.getMemberId();
        addMemberToSociety(societyId, userId, memberId);
    }

    private void addMemberToSociety(long societyId, long userId, long memberId) {
        // 해당 모임에 운영자 이상의 권한이 있는지 확인
        checkSocietyMemberAuth(societyId, userId, MemberType.OPERATOR);
        // 모임 가입을 허용할 멤버가 이미 가입 되어 있는지 확인
        if (societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(societyId, memberId)) {
            throw new RestException(ErrorCode.FAIL_ALLOW_MEMBER_ALREADY_SOCIETY);
        }
        // 모임 대기 멤버 목록 취득
        SocietyMemberWait societyMemberWait = getSocietyMemberWait(societyId, memberId);
        // 모임 대기 멤버 목록 삭제
        if(societyMemberWait != null) {
            societyMemberWaitRepository.delete(societyMemberWait);
        }
        // 모임 멤버 추가
        societyMemberRepository.save(SocietyMember.builder()
                .userId(memberId)              // 추가될 멤버 고유번호
                .societyId(societyId)          // 추가될 모임 고유번호
                .memberType(MemberType.NORMAL) // 추가될 멤버 권한 (일반)
                .build());
    }

    // accept society member (초대를 승인 한다)
    public void acceptSocietyMember(SocietyMemberAcceptParam societyMemberAcceptParam) {
        long societyId = societyMemberAcceptParam.getSocietyId();
        long userId = societyMemberAcceptParam.getUserId();
        // 모임 가입을 허용할 멤버가 이미 가입 되어 있는지 확인
        if (societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(societyId, userId)) {
            throw new RestException(ErrorCode.FAIL_ACCEPT_MEMBER_ALREADY_SOCIETY);
        }
        SocietyMemberWait societyMemberWait = getSocietyMemberWait(societyId, userId);
        societyMemberWaitRepository.delete(societyMemberWait);

        // 모임 멤버 추가 됨
        societyMemberRepository.save(SocietyMember.builder()
                .userId(userId)                // 추가될 멤버 고유번호
                .societyId(societyId)          // 추가될 모임 고유번호
                .memberType(MemberType.NORMAL) // 추가될 멤버 권한 (일반)
                .build());
    }

    // remove society member ( check leader auth)
    @Transactional(rollbackFor = {Exception.class})
    public void removeSocietyMember(SocietyMemberRemoveParam societyMemberRemoveParam) {
        // 모임에 멤버인지 확인
        SocietyMember targetSocietyMember = getSocietyMember(societyMemberRemoveParam.getSocietyId(), societyMemberRemoveParam.getMemberId());
        // 해당 모임에 운영자 이상의 권한이 있는지 확인
        checkSocietyMemberAuth(societyMemberRemoveParam.getSocietyId(), societyMemberRemoveParam.getUserId(), MemberType.OPERATOR);
        // 해당 인원 모임에서 제거
        societyMemberRepository.delete(targetSocietyMember);
    }

    // leave society
    @Transactional(rollbackFor = {Exception.class})
    public void leaveSociety(SocietyMemberLeaveParam societyMemberLeaveParam) {
        // 모임에 멤버인지 확인
        SocietyMember targetSocietyMember = getSocietyMember(societyMemberLeaveParam.getSocietyId(), societyMemberLeaveParam.getUserId());
        // 리더의 경우 모임을 탈퇴할 수 없음
        if (targetSocietyMember.getMemberType() == MemberType.LEADER) {
            throw new RestException(ErrorCode.FAIL_LEAVE_SOCIETY_MEMBER_LEADER);
        }
        // 해당 모임에서 탈퇴
        societyMemberRepository.delete(targetSocietyMember);
    }

    // 모임 취득
    private Society getSocietyEntity(long societyId) {
        return societyRepository.findById(societyId)
                        .orElseThrow(() -> new RestException(ErrorCode.NOT_EXIST_SOCIETY));
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


    public void changeSocietyMemberAuthority(SocietyMemberAuthorityParam societyMemberAuthorityParam) {
        long societyId = societyMemberAuthorityParam.getSocietyId();
        long userId = societyMemberAuthorityParam.getUserId();
        long memberId = societyMemberAuthorityParam.getMemberId();
        MemberType memberType = societyMemberAuthorityParam.getMemberType();
        // 해당 모임에 운영자 이상의 권한이 있는지 확인
        checkSocietyMemberAuth(societyId, userId, MemberType.OPERATOR);
        // 해당 인원의 권한 변경
        SocietyMember targetSocietyMember = getSocietyMember(societyId, memberId);
        targetSocietyMember.setMemberType(memberType);
        societyMemberRepository.save(targetSocietyMember);
    }
}