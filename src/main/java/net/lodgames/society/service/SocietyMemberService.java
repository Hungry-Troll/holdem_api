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
    private final SocietyRepository societyRepository;
    private final SocietyQueryRepository societyQueryRepository;
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
    // 모임 가입 유형이 허가제인 경우 대기 멤버로 추가됨
    // 모임 가입 유형이 비번인 경우 비번 확인 후 가입됨
    // 모임 가입 유형이 자유인 경우 바로 가입됨
    @Transactional(rollbackFor = {Exception.class})
    public void joinSocietyMember(SocietyMemberParam societyMemberParam) {
        long societyId = societyMemberParam.getSocietyId();
        long userId = societyMemberParam.getUserId();
        // 리더 고유번호, 모임 가입 형태 , 비번
        SocietyLeaderVo societyLeaderVo = societyQueryRepository.selectSocietyAndSocietyLeader(societyId);
        if (societyLeaderVo == null) {
            // 모임이 존재하지 않음
            throw new RestException(ErrorCode.SOCIETY_NOT_EXIST);
        }
        JoinType joinType = societyLeaderVo.getJoinType();
        // long leaderIdx = societyLeaderVo.getUserId();
        // check already member
        if (societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(societyId, userId)) {
            // 이미 가입된 유저
            throw new RestException(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER);
        }

        // 가입 형태가 허가제 인경우
        if (joinType == JoinType.PERMIT) {
            // 모임 대기 멤버 목록 취득
            if (societyMemberWaitRepository.existsSocietyMemberWaitBySocietyIdAndUserId(societyId, userId)) {
                // 이미 대기중인 유저
                throw new RestException(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER_WAIT);
            }
            // 모임 멤버 추가
            societyMemberWaitRepository.save(SocietyMemberWait.builder().userId(userId)           // 추가될 유저 고유아이디
                    .societyId(societyId)     // 추가될 모임 고유아이디
                    .waitType(WaitType.APPLY) // 추가될 멤버 대기 상태 (신청)
                    .build());
        } else {
            // 비번 가입시 비번 확인
            if (joinType == JoinType.LOCK) {
                checkPassword(societyMemberParam.getPasscode(), societyLeaderVo.getPasscode());
            }
            // 모임 멤버 추가
            societyMemberRepository.save(SocietyMember.builder().userId(userId)                // 추가될 멤버 고유번호
                    .societyId(societyId)          // 추가될 모임 고유번호
                    .memberType(MemberType.NORMAL) // 추가될 멤버 권한 (일반)
                    .build());
        }
    }

    // 모임 멤버 초대 (멤버가 수락시 정식 멤버가 됨)
    @Transactional(rollbackFor = {Exception.class})
    public void inviteSocietyMember(SocietyMemberInviteParam societyMemberInviteParam) {
        long societyId = societyMemberInviteParam.getSocietyId(); // 해당 모임 고유번호
        long userId = societyMemberInviteParam.getUserId();       // 초대하는 유저 고유번호
        long memberId = societyMemberInviteParam.getMemberId();   // 초대할 유저 고유번호
        // 초대하는 유저가 해당 모임 가입자인지 확인
        if (!societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(societyId, userId)) {
            throw new RestException(ErrorCode.NO_AUTH_SOCIETY_MEMBER); // 해당 모임 멤버만 초대 가능
        }
        // 초대 하려는 유자가 이미 모임에 가입되어 있는지 확인
        if (societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(societyId, memberId)) {
            throw new RestException(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER); // 이미 가입된 유저
        }

        // 모임 대기 멤버 목록 취득
        societyMemberWaitRepository.findBySocietyIdAndUserId(societyId, userId)
                .ifPresentOrElse(societyMemberWait -> {
                    WaitType waitType = societyMemberWait.getWaitType(); // 대기 타입
                    // 지원 대기중인 경우
                    if (waitType == WaitType.APPLY) {
                        // 이미 지원 대기중인 유저 삭제
                        societyMemberWaitRepository.delete(societyMemberWait);
                        // 모임 멤버 추가
                        societyMemberRepository.save(SocietyMember.builder().userId(memberId)              // 추가될 멤버 고유번호
                                .societyId(societyId)          // 추가될 모임 고유번호
                                .memberType(MemberType.NORMAL) // 추가될 멤버 권한 (일반)
                                .build());
                        // 초대 대기인 경우
                    } else if (waitType == WaitType.INVITE) {
                        // 이미 초대된 유저
                        throw new RestException(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER_WAIT);
                    }
                }, () -> {
                    // 모임 멤버 추가
                    societyMemberWaitRepository.save(SocietyMemberWait.builder().userId(memberId)          // 추가될 유저 고유아이디
                            .societyId(societyId)      // 추가될 모임 고유아이디
                            .waitType(WaitType.INVITE) // 추가될 멤버 대기 상태 (신청)
                            .build());
                });
    }

    // 비밀번호 확인
    protected void checkPassword(String inputPass, String pass) throws RestException {
        if (StringUtils.isNullOrEmpty(inputPass) || !inputPass.equals(pass)) {
            throw new RestException(ErrorCode.FAIL_ADD_MEMBER_WRONG_PASSWORD);
        }
    }

    // 모임 가입을 허용 한다 (운영자 이상 권한)
    @Transactional(rollbackFor = {Exception.class})
    public void allowSocietyMember(SocietyMemberAllowParam societyMemberAllowParam) {
        long societyId = societyMemberAllowParam.getSocietyId(); // 가입 모임 고유번호
        long userId = societyMemberAllowParam.getUserId();       // 모입 가입을 허락할 유저 고유번호
        long memberId = societyMemberAllowParam.getMemberId();   // 가입 허락을 기다리는 유저 고유번호
        // 모입 가입을 허락할 유저가 해당 모임에 운영자 이상의 권한이 있는지 확인 (모임에 가입을 수락할 수 있는 권한이 있는지 확인)
        checkMemberAuthWithSociety(societyId, userId, MemberType.OPERATOR);
        // 가입 허락을 기다리는 유저의 모임 대기 리스트에서 제거
        deleteMemberWaitIfExist(societyId, memberId);
        // 가입 허락을 기다리는 유저의 모임 가입 처리
        addMemberToSociety(societyId, memberId);
    }

    // 모임장 또는 모임 운영자가 모임 초대 수락시 대기 리스트에서 삭제되고 정식 멤버가 됨
    @Transactional(rollbackFor = {Exception.class})
    public void acceptSocietyMember(SocietyMemberAcceptParam societyMemberAcceptParam) {
        long societyId = societyMemberAcceptParam.getSocietyId(); // 해당 모임 아이디
        long memberId = societyMemberAcceptParam.getUserId();     // 초대 수락 유저 고유번호
        // 모임 대기 리스트 삭제
        deleteMemberWaitIfExist(societyId, memberId);
        // 모임 가입 처리
        addMemberToSociety(societyId, memberId);
    }

    // 모임 대기 삭제
    private void deleteMemberWaitIfExist(long societyId, long memberId) {
        // 모임 대기 멤버 목록 취득
        SocietyMemberWait societyMemberWait = societyMemberWaitRepository
                .findBySocietyIdAndUserId(societyId, memberId)
                .orElseThrow(() -> new RestException(ErrorCode.MEMBER_WAIT_NOT_EXIST));
        societyMemberWaitRepository.delete(societyMemberWait);
    }

    // 모임 가입 처리
    private void addMemberToSociety(long societyId, long memberId) {
        // 모임 가입을 허용할 멤버가 이미 가입 되어 있는지 확인
        if (societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(societyId, memberId)) {
            throw new RestException(ErrorCode.FAIL_ALLOW_MEMBER_ALREADY_SOCIETY);
        }
        // 모임 멤버 추가
        societyMemberRepository.save(SocietyMember.builder()
                .userId(memberId)              // 추가될 멤버 고유번호
                .societyId(societyId)          // 추가될 모임 고유번호
                .memberType(MemberType.NORMAL) // 추가될 멤버 권한 (일반)
                .build());
    }


    // 모임에서 멤버를 제거 한다 (운영자 이상 권한)
    @Transactional(rollbackFor = {Exception.class})
    public void removeSocietyMember(SocietyMemberRemoveParam societyMemberRemoveParam) {
        // 해당 모임에 운영자 이상의 권한이 있는지 확인
        checkMemberAuthWithSociety(societyMemberRemoveParam.getSocietyId(), societyMemberRemoveParam.getUserId(), MemberType.OPERATOR);
        // 모임에 멤버인지 확인
        SocietyMember targetSocietyMember = getSocietyMember(societyMemberRemoveParam.getSocietyId(), societyMemberRemoveParam.getMemberId());
        // 해당 인원 모임에서 제거
        societyMemberRepository.delete(targetSocietyMember);
    }

    // 모임에서 탈퇴 한다 (리더는 탈퇴 불가)
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
        return societyRepository.findById(societyId).orElseThrow(() -> new RestException(ErrorCode.SOCIETY_NOT_EXIST));
    }

    // 해당 모임에 필요한 권한(MemberType) 이상의 권한이 있는지 확인
    private void checkMemberAuthWithSociety(long societyId, long userId, MemberType memberType) {
        // 모임 멤버 정보 취득
        SocietyMember societyMember = getSocietyMember(societyId, userId);
        // 모임 멤버 권한
        long type_num = societyMember.getMemberType().getTypeNum();
        // 권한 확인
        if (type_num < memberType.getTypeNum()) {
            // No auth
            throw new RestException(ErrorCode.NO_AUTH_SOCIETY_MEMBER);
        }
    }

    // 모임 멤버 취득 (모임아이디 , 유저아이디)
    private SocietyMember getSocietyMember(long societyId, long userId) {
        return societyMemberRepository.findBySocietyIdAndUserId(societyId, userId).orElseThrow(() -> new RestException(ErrorCode.NOT_EXIST_SOCIETY_MEMBER));
    }

    // 모임 멤버 권한 변경
    @Transactional(rollbackFor = {Exception.class})
    public void changeSocietyMemberAuthority(SocietyMemberAuthorityParam societyMemberAuthorityParam) {
        long societyId = societyMemberAuthorityParam.getSocietyId();            // 해당 모임 고유번호
        long userId = societyMemberAuthorityParam.getUserId();                  // 멤버 권한 변경 유저 고유번호
        long memberId = societyMemberAuthorityParam.getMemberId();              // 변경할 멤버 고유번호
        MemberType newMemberType = societyMemberAuthorityParam.getMemberType(); // 변경할 멤버 권한
        // 변경할 멤버의 정보를 취득
        SocietyMember targetMember = getSocietyMember(societyId, memberId);
        // 변경할 인원이 리더 인지 확인
        if (targetMember.getMemberType() == MemberType.LEADER) {
            // 리더는 권한 변경 불가
            throw new RestException(ErrorCode.FAIL_CHANGE_SOCIETY_MEMBER_AUTHORITY_LEADER);
        }
        // 권한 변경 로직 분리
        if (newMemberType == MemberType.LEADER) {
            // 리더 권한 변경을 위해 요청자가 리더인지 확인
            checkMemberAuthWithSociety(societyId, userId, MemberType.LEADER);

            // 기존 리더를 일반 멤버로 변경
            SocietyMember currentLeader = getSocietyMember(societyId, userId);
            currentLeader.setMemberType(MemberType.NORMAL);
            societyMemberRepository.save(currentLeader);
        } else {
            // 운영자 이상의 권한 확인
            checkMemberAuthWithSociety(societyId, userId, MemberType.OPERATOR);
        }

        // 대상 멤버를 리더로 변경
        targetMember.setMemberType(MemberType.LEADER);
        societyMemberRepository.save(targetMember);
    }
}