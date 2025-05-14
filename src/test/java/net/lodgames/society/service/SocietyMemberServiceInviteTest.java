package net.lodgames.society.service;

import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.society.constants.SocietyDummy;
import net.lodgames.society.constants.WaitType;
import net.lodgames.society.model.SocietyMember;
import net.lodgames.society.model.SocietyMemberWait;
import net.lodgames.society.param.member.SocietyMemberInviteParam;
import net.lodgames.society.repository.SocietyMemberRepository;
import net.lodgames.society.repository.SocietyMemberWaitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static net.lodgames.society.constants.SocietyTestConstants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SocietyMemberServiceInviteTest {

    @InjectMocks
    SocietyMemberService societyMemberService;

    @Mock
    SocietyMemberRepository societyMemberRepository;

    @Mock
    SocietyMemberWaitRepository societyMemberWaitRepository;

    private SocietyMemberInviteParam getBasicSocietyMemberInviteParam() {
        return SocietyMemberInviteParam.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID)  // 모임 ID
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)        // 초대한 유저 ID
                .memberId(SocietyDummy.SOCIETY_MEMBER_USER_ID)      // 초대할 유저 ID
                .build();
    }

    @Test
    @Rollback
    void INVITE_SOCIETY_MEMBER_THEN_SOCIETY_MEMBER_WAIT_ADDED() {
        // 모임 초대 기본 파라미터
        SocietyMemberInviteParam societyMemberInviteParam = getBasicSocietyMemberInviteParam();

        // 모임 가입여부 확인
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(USER_IS_MEMBER)      // 초대하려는 유저가 해당 모임 가입자인지 확인 (유저임)
                .thenReturn(USER_IS_NOT_MEMBER); // 모입에 가입되어 있지 않은 유저인지 확인 (유저 아님 = 가입 안 되어 있음)

        // 모임대기 멥버에 등록되어 있지 않음
        when(societyMemberWaitRepository.findBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.empty());
        societyMemberService.inviteSocietyMember(societyMemberInviteParam);
        verify(societyMemberWaitRepository).save(any(SocietyMemberWait.class));
    }

    @Test
    @Rollback
    void INVITE_SOCIETY_APPLIED_MEMBER_THEN_SOCIETY_MEMBER_ADDED() {
        // 모임 초대 기본 파라미터
        SocietyMemberInviteParam societyMemberInviteParam = getBasicSocietyMemberInviteParam();
        SocietyMemberWait societyMemberWait = SocietyMemberWait.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 모임 ID
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)       // 초대한 유저 ID
                .waitType(WaitType.APPLY)                          // 가입 대기 타입
                .build();

        // 모임 가입여부 확인
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(USER_IS_MEMBER)      // 1st : 초대하려는 유저가 해당 모임 가입자인지 확인 (유저임)
                .thenReturn(USER_IS_NOT_MEMBER); // 2nd : 모입에 가입되어 있지 않은 유저인지 확인 (유저 아님 = 가입 안 되어 있음)

        // 모임대기 멥버에 등록되어 있음
        when(societyMemberWaitRepository.findBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(societyMemberWait));
        // 모임 초대
        societyMemberService.inviteSocietyMember(societyMemberInviteParam);
        // 모임 초대된 유저가 멤버로 저장 되었는지 확인
        verify(societyMemberRepository).save(any(SocietyMember.class));
    }

    @Test
    @Rollback
    void INVITE_SOCIETY_ALREADY_INVITED_MEMBER_THEN_OCCUR_ERROR() {
        // 모임 초대 기본 파라미터
        SocietyMemberInviteParam societyMemberInviteParam = getBasicSocietyMemberInviteParam();
        SocietyMemberWait societyMemberWait = SocietyMemberWait.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 모임 ID
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)       // 초대한 유저 ID
                .waitType(WaitType.INVITE)                         // 가입 대기 타입
                .build();

        // 모임 가입여부 확인
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(USER_IS_MEMBER)      // 1st : 초대하려는 유저가 해당 모임 가입자인지 확인 (유저임)
                .thenReturn(USER_IS_NOT_MEMBER); // 2nd : 모입에 가입되어 있지 않은 유저인지 확인 (유저 아님 = 가입 안 되어 있음)

        // 모임대기 멥버에 등록되어 있음
        when(societyMemberWaitRepository.findBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(societyMemberWait));

        // 모임 초대
        assertThatThrownBy(() -> societyMemberService.inviteSocietyMember(societyMemberInviteParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER_WAIT);
    }

    @Test
    @Rollback
    void INVITE_SOCIETY_MEMBER_FROM_NONE_MEMBER_USER_THEN_OCCUR_ERROR() {
        // 모임 초대 기본 파라미터
        SocietyMemberInviteParam societyMemberInviteParam = getBasicSocietyMemberInviteParam();

        // 모임 가입여부 확인
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(USER_IS_NOT_MEMBER);      // 초대하려는 유저가 해당 모임 가입자인지 확인 (유저 아님!!)
        // 모임 초대
        assertThatThrownBy(() -> societyMemberService.inviteSocietyMember(societyMemberInviteParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NO_AUTH_SOCIETY_MEMBER);
    }

    @Test
    @Rollback
    void INVITE_ALREADY_SOCIETY_MEMBER_THEN_OCCUR_ERROR() {
        // 모임 초대 기본 파라미터
        SocietyMemberInviteParam societyMemberInviteParam = getBasicSocietyMemberInviteParam();

        // 모임 가입여부 확인
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(USER_IS_MEMBER)  // 초대하려는 유저가 해당 모임 가입자인지 확인 (유저임)
                .thenReturn(USER_IS_MEMBER); // 모입에 가입되어 있지 않은 유저인지 확인 (유저임!! = 이미 가입되어 있음)
        // 모임 초대
        assertThatThrownBy(() -> societyMemberService.inviteSocietyMember(societyMemberInviteParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER);
    }
}