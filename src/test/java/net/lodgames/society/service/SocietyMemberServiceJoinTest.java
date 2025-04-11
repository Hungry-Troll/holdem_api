package net.lodgames.society.service;

import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.society.constants.JoinType;
import net.lodgames.society.constants.SocietyDummy;
import net.lodgames.society.model.SocietyMember;
import net.lodgames.society.model.SocietyMemberWait;
import net.lodgames.society.param.member.SocietyMemberParam;
import net.lodgames.society.repository.SocietyMemberRepository;
import net.lodgames.society.repository.SocietyMemberWaitRepository;
import net.lodgames.society.repository.SocietyQueryRepository;
import net.lodgames.society.vo.SocietyLeaderVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import static net.lodgames.society.constants.SocietyTestConstants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SocietyMemberServiceJoinTest {

    @InjectMocks
    SocietyMemberService societyMemberService;

    @Mock
    SocietyQueryRepository societyQueryRepository;

    @Mock
    SocietyMemberRepository societyMemberRepository;

    @Mock
    SocietyMemberWaitRepository societyMemberWaitRepository;


    private SocietyMemberParam getBasicSocietyMemberParam() {
        return SocietyMemberParam.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID)
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)
                .build();
    }


    @Test
    @Rollback
    void JOIN_FREE_SOCIETY_MEMBER_THEN_SOCIETY_MEMBER_ADDED() {
        // 모임 가입 파라미터
        SocietyMemberParam societyMemberParam = getBasicSocietyMemberParam();
        // [자유] 가입 모임 리더 및 가입 타입 정보
        SocietyLeaderVo societyLeaderVo = SocietyLeaderVo.builder()
                .joinType(JoinType.FREE)
                .userId(SocietyDummy.SOCIETY_LEADER_USER_ID)
                .build();

        // 리더 고유번호, 모임 가입 형태 (, 비번) 획득
        when(societyQueryRepository.selectSocietyAndSocietyLeader(anyLong())).thenReturn(societyLeaderVo);
        // 모입에 가입되어 있지 않은 유저
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(USER_IS_NOT_MEMBER);
        when(societyMemberRepository.save(any(SocietyMember.class))).thenReturn(any(SocietyMember.class));
        societyMemberService.joinSocietyMember(societyMemberParam);
        verify(societyMemberRepository).save(any(SocietyMember.class));
    }

    @Test
    @Rollback
    void JOIN_PERMIT_SOCIETY_MEMBER_THEN_SOCIETY_MEMBER_ADDED() {
        // 모임 가입 파라미터
        SocietyMemberParam societyMemberParam = getBasicSocietyMemberParam();
        // [허가] 가입 모임 리더 및 가입 타입 정보
        SocietyLeaderVo societyLeaderVo = SocietyLeaderVo.builder()
                .joinType(JoinType.PERMIT)
                .userId(SocietyDummy.SOCIETY_LEADER_USER_ID)
                .build();
        // 리더 고유번호, 모임 가입 형태 (, 비번) 획득
        when(societyQueryRepository.selectSocietyAndSocietyLeader(anyLong())).thenReturn(societyLeaderVo);
        // 모입에 가입되어 있지 않은 유저
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(USER_IS_NOT_MEMBER);
        // 가입 대기중인 유저
        when(societyMemberWaitRepository.existsSocietyMemberWaitBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(USER_IS_NOT_WAIT_MEMBER);
        // 가입 대기중인 유저 정보 저장
        when(societyMemberWaitRepository.save(any(SocietyMemberWait.class))).thenReturn(any(SocietyMemberWait.class));
        societyMemberService.joinSocietyMember(societyMemberParam);
        verify(societyMemberWaitRepository).save(any(SocietyMemberWait.class));
    }

    @Test
    @Rollback
    void JOIN_LOCK_SOCIETY_MEMBER_THEN_SOCIETY_MEMBER_ADDED() {
        // 모임 가입 파라미터 (비번)
        SocietyMemberParam societyMemberParam = SocietyMemberParam.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 모임 ID
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)       // 가입할 유저 ID
                .passcode(SocietyDummy.SOCIETY_PASSCODE)           // 가입할 비번
                .build();
        // [비번] 가입 모임 리더 및 가입 타입 정보
        SocietyLeaderVo societyLeaderVo = SocietyLeaderVo.builder()
                .joinType(JoinType.LOCK)                     // 가입 타입
                .passcode(SocietyDummy.SOCIETY_PASSCODE)     // 가입 비번
                .userId(SocietyDummy.SOCIETY_LEADER_USER_ID) // 리더 ID
                .build();
        // 리더 고유번호, 모임 가입 형태 (, 비번) 획득
        when(societyQueryRepository.selectSocietyAndSocietyLeader(anyLong())).thenReturn(societyLeaderVo);
        // 모입에 가입되어 있지 않은 유저
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(USER_IS_NOT_MEMBER);
        // 회원정보 저장
        when(societyMemberRepository.save(any(SocietyMember.class))).thenReturn(any(SocietyMember.class));
        societyMemberService.joinSocietyMember(societyMemberParam);
        verify(societyMemberRepository).save(any(SocietyMember.class));
    }

    // 비번 가입 모임에 가입하려는 유저가 비번을 잘못 입력한 경우, 에러 발생 (비번 틀림) FAIL_ADD_MEMBER_WRONG_PASSWORD
    @Test
    void JOIN_LOCK_SOCIETY_MEMBER_WITH_WRONG_PASSCODE_THEN_OCCUR_ERROR() {
        SocietyMemberParam societyMemberParam = SocietyMemberParam.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID) // 모임 ID
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)       // 가입할 유저 ID
                .passcode("wrong_passcode")                        // 가입할 비번
                .build();
        SocietyLeaderVo societyLeaderVo = SocietyLeaderVo.builder()
                .joinType(JoinType.LOCK)
                .passcode(SocietyDummy.SOCIETY_PASSCODE)
                .userId(SocietyDummy.SOCIETY_LEADER_USER_ID)
                .build();
        // 리더 고유번호, 모임 가입 형태 (, 비번) 획득
        when(societyQueryRepository.selectSocietyAndSocietyLeader(anyLong())).thenReturn(societyLeaderVo);
        // 모입에 가입되어 있지 않은 유저
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(USER_IS_NOT_MEMBER);
        // 회원정보 저장
        // when(societyMemberRepository.save(any(SocietyMember.class))).thenReturn(any(SocietyMember.class));
        assertThatThrownBy(() -> societyMemberService.joinSocietyMember(societyMemberParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ADD_MEMBER_WRONG_PASSWORD);
    }


    // 가입하려는 모임이 존재하지 않는 경우, 에러 발생 ( 모임이 존재하지 않음) SOCIETY_NOT_EXIST
    @Test
    void JOIN_NOT_EXISTS_SOCIETY_MEMBER_THEN_OCCUR_ERROR() {
        // 모임 가입 파라미터
        SocietyMemberParam societyMemberParam = getBasicSocietyMemberParam();

        // 리더 고유번호, 모임 가입 형태 (, 비번) 획득
        when(societyQueryRepository.selectSocietyAndSocietyLeader(anyLong())).thenReturn(null);
        // 에러 발생 ( 모임이 존재하지 않음)
        assertThatThrownBy(() -> societyMemberService.joinSocietyMember(societyMemberParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.SOCIETY_NOT_EXIST);
    }

    // 모임에 가입하려는 유저가 이미 가입되어 있는 경우, 에러 발생 (모임에 이미 가입되어 있음)
    @Test
    void JOIN_SOCIETY_READER_MEMBER_THEN_SOCIETY_MEMBER_OCCUR_ERROR() {
        // 모임 가입 파라미터
        SocietyMemberParam societyMemberParam = getBasicSocietyMemberParam();
        // [자유] 가입 모임 리더 및 가입 타입 정보
        SocietyLeaderVo societyLeaderVo = SocietyLeaderVo.builder()
                .joinType(JoinType.FREE)
                .userId(SocietyDummy.SOCIETY_LEADER_USER_ID)
                .build();

        // 리더 고유번호, 모임 가입 형태 (, 비번) 획득
        when(societyQueryRepository.selectSocietyAndSocietyLeader(anyLong())).thenReturn(societyLeaderVo);
        // 모입에 가입되어 있지 않은 유저
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(USER_IS_MEMBER);
        // 에러 발생 ( 모임에 이미 가입되어 있음)
        assertThatThrownBy(() -> societyMemberService.joinSocietyMember(societyMemberParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER);
    }

    @Test
    @Rollback
    void JOIN_PERMIT_SOCIETY_WAITING_MEMBER_THEN_OCCUR_ERROR() {
        // 모임 가입 파라미터
        SocietyMemberParam societyMemberParam = getBasicSocietyMemberParam();
        // [허가] 가입 모임 리더 및 가입 타입 정보
        SocietyLeaderVo societyLeaderVo = SocietyLeaderVo.builder()
                .joinType(JoinType.PERMIT)
                .userId(SocietyDummy.SOCIETY_LEADER_USER_ID)
                .build();
        // 리더 고유번호, 모임 가입 형태 (, 비번) 획득
        when(societyQueryRepository.selectSocietyAndSocietyLeader(anyLong())).thenReturn(societyLeaderVo);
        // 모입에 가입되어 있지 않은 유저
        when(societyMemberRepository.existsSocietyMemberBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(USER_IS_NOT_MEMBER);
        // 가입 대기중인 유저
        when(societyMemberWaitRepository.existsSocietyMemberWaitBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(USER_IS_WAIT_MEMBER);
        // 에러 발생 ( 모임에 이미 가입되어 있음)
        assertThatThrownBy(() -> societyMemberService.joinSocietyMember(societyMemberParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ALREADY_SOCIETY_MEMBER_WAIT);
    }


}
