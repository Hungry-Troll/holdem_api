package net.lodgames.society.service;

import net.lodgames.society.constants.MemberType;
import net.lodgames.society.constants.SocietyDummy;
import net.lodgames.society.constants.WaitType;
import net.lodgames.society.model.SocietyMember;
import net.lodgames.society.model.SocietyMemberWait;
import net.lodgames.society.param.member.SocietyMemberAcceptParam;
import net.lodgames.society.param.member.SocietyMemberAllowParam;
import net.lodgames.society.repository.SocietyMemberRepository;
import net.lodgames.society.repository.SocietyMemberWaitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static net.lodgames.society.model.QSocietyMemberWait.societyMemberWait;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SocietyMemberServiceTest {

    // 모임 멤버 서비스
    @InjectMocks
    SocietyMemberService societyMemberService;

    // 모임 멤버 리포지토리
    @Mock
    SocietyMemberRepository societyMemberRepository;

    // 모임 멤버 대기 리포지토리
    @Mock
    SocietyMemberWaitRepository societyMemberWaitRepository;


    // 모임 가입을 허용할 유저
    @Test
    @Rollback
    void allow_society_member_then_society_member_added() {
        // 모임 가입 파라미터
        SocietyMemberAllowParam societyMemberAllowParam = SocietyMemberAllowParam.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 가입 모임 고유번호
                .userId(SocietyDummy.SOCIETY_LEADER_USER_ID)       // 모입 가입을 허락할 유저 고유번호
                .memberId(SocietyDummy.SOCIETY_MEMBER_USER_ID)     // 가입 허락을 기다리는 유저 고유번호
                .build();

        // 모임 가입을 허락할 유저 정보 (actor)
        SocietyMember societyDoAllowMember = SocietyMember.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 가입 모임 고유번호
                .userId(SocietyDummy.SOCIETY_LEADER_USER_ID)       // 가입을 허락할 유저 고유번호
                .memberType(MemberType.LEADER)                     // 가입을 허락할 유저 권한 (리더)
                .build();

        // 모임 가입 허락을 기다리는 유저 대기 정보 (target)
        SocietyMemberWait societyMemberWait = SocietyMemberWait.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 가입 모임 고유번호
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)       // 가입 허락을 기다리는 유저 고유번호
                .waitType(WaitType.APPLY)                          // 가입 대기 타입 (가입신청)
                .build();

        // 모임 가입 허락 대기 상태에서 추가된 (모임가입이 허락 된) 모임 멤버 정보
        SocietyMember societyJoinMember = SocietyMember.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 가입 모임 고유번호
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)       // 모임가입이 허락 된 유저 고유번호
                .memberType(MemberType.NORMAL)                     // 추가될 멤버 권한 (일반)
                .build();

        when(societyMemberRepository.findBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(societyDoAllowMember)) // 1st : 모임 가입을 허락할 유저 정보 (actor) 취득 결과
                .thenReturn(Optional.of(societyJoinMember));   // 2nd : 모임 가입 허락을 기다리는 유저 대기 정보 (target) 취득 결과

        // 모임을 가입시킬 유저 대기 정보 취득
        when(societyMemberWaitRepository.findBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(societyMemberWait));

        // 대기리스트 삭제시 아무 것도 반환 하지 않는다
        doNothing().when(societyMemberWaitRepository).delete(any(SocietyMemberWait.class));

        // 이미 가입된 유저인지 확인
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(Boolean.FALSE);

        // 모임 가입 요청 (Test)
        societyMemberService.allowSocietyMember(societyMemberAllowParam);

        // 모임 가입 요청시 멤버 정보 추가 확인
        verify(societyMemberRepository).save(any(SocietyMember.class));
    }

    /**
     * 모임 초대 수락시 대기 리스트에서 삭제되고 정식 멤버가 됨
     */
    @Test
    @Rollback
    void accept_society_member_then_society_member_added() {
        // 모임 초대 수락 파라미터
        SocietyMemberAcceptParam societyMemberAcceptParam = SocietyMemberAcceptParam.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID)
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)
                .build();

        // 모임 초대 대기 상태에서 추가된 (모임가입이 허락 한) 모임 멤버 정보
        SocietyMemberWait societyMemberWait = SocietyMemberWait.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 가입 모임 고유번호
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)       // 가입 허락을 기다리는 유저 고유번호
                .waitType(WaitType.APPLY)                          // 가입 대기 타입 (가입신청)
                .build();

        // 모임 초대받은 유저 대기 정보 (target) 취득 결과
        when(societyMemberWaitRepository.findBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(societyMemberWait));

        // 초대리스트 삭제시 아무 것도 반환 하지 않는다.
        doNothing().when(societyMemberWaitRepository).delete(any(SocietyMemberWait.class));

        // 이미 가입된 유저인지 확인
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(Boolean.FALSE);

        // 모임 초대 수락 (Test)
        societyMemberService.acceptSocietyMember(societyMemberAcceptParam);

        // 모임 초대 수락시 멤버 정보 추가 확인
        verify(societyMemberRepository).save(any(SocietyMember.class));

    }

    @Test
    @Rollback
    void remove_society_member_then_society_member_deleted() {
        // 모임 멤버를 삭제할 유저 정보 (actor)
        SocietyMember societyDoDeleteMember = SocietyMember.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 가입 모임 고유번호
                .userId(SocietyDummy.SOCIETY_LEADER_USER_ID)       // 가입을 허락할 유저 고유번호
                .memberType(MemberType.LEADER)                     // 가입을 허락할 유저 권한 (리더)
                .build();

        when(societyMemberRepository.findBySocietyIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(societyDoDeleteMember)); // 1st : 모임 가입을 허락할 유저 정보 (actor) 취득 결과
    }



}
