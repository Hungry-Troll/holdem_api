package net.lodgames.society.service;

import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.society.constants.JoinType;
import net.lodgames.society.constants.MemberType;
import net.lodgames.society.constants.SocietyDummy;
import net.lodgames.society.model.Society;
import net.lodgames.society.model.SocietyMember;
import net.lodgames.society.param.*;
import net.lodgames.society.repository.SocietyMemberRepository;
import net.lodgames.society.repository.SocietyMemberWaitRepository;
import net.lodgames.society.repository.SocietyQueryRepository;
import net.lodgames.society.repository.SocietyRepository;
import net.lodgames.society.util.SocietyMapper;
import net.lodgames.society.util.TagUtils;
import net.lodgames.society.vo.SocietyInfoVo;
import net.lodgames.society.vo.SocietyVo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocietyServiceTest extends SocietyServiceDummy {

    @InjectMocks
    SocietyService societyService;
    @Mock
    SocietyRepository societyRepository;
    @Mock
    SocietyMemberRepository societyMemberRepository;
    @Mock
    SocietyQueryRepository societyQueryRepository;
    @Mock
    SocietyMemberWaitRepository societyMemberWaitRepository;
    @Spy
    SocietyMapper societyMapper = Mappers.getMapper(SocietyMapper.class); // 매핑 클래스 null point exception 회피

    private SocietyAddParam makeDummySocietyAddParam() {
        return SocietyAddParam.builder()
                .name(SocietyDummy.SOCIETY_NAME_1)
                .joinType(JoinType.PERMIT)
                .info(SocietyDummy.SOCIETY_INFO_1)
                .image(SocietyDummy.SOCIETY_IMAGE_1)
                .backImage(SocietyDummy.SOCIETY_BACK_IMAGE_1)
                .tags(SocietyDummy.SOCIETY_TAGS_1)
                .build();
    }

    @Test
    void ADD_SOCIETY_THEN_SOCIETY_ADDED() {
        Society society = makeDummySociety();
        SocietyAddParam societyAddParam = SocietyAddParam.builder()
                .name(SocietyDummy.SOCIETY_NAME_1)
                .joinType(JoinType.PERMIT)
                .info(SocietyDummy.SOCIETY_INFO_1)
                .image(SocietyDummy.SOCIETY_IMAGE_1)
                .backImage(SocietyDummy.SOCIETY_BACK_IMAGE_1)
                .tags(SocietyDummy.SOCIETY_TAGS_1)
                .build();
        when(societyMemberRepository.countByUserIdAndMemberType(societyAddParam.getUserId(), MemberType.LEADER)).thenReturn(0);
        when(societyRepository.save(any(Society.class))).thenReturn(society);
        SocietyInfoVo societyInfoVo = societyService.addSociety(societyAddParam);
        Assertions.assertThat(societyInfoVo.getName()).isEqualTo(SocietyDummy.SOCIETY_NAME_1);
        Assertions.assertThat(societyInfoVo.getJoinType()).isEqualTo(JoinType.PERMIT);
        Assertions.assertThat(societyInfoVo.getInfo()).isEqualTo(SocietyDummy.SOCIETY_INFO_1);
        Assertions.assertThat(societyInfoVo.getImage()).isEqualTo(SocietyDummy.SOCIETY_IMAGE_1);
        Assertions.assertThat(societyInfoVo.getBackImage()).isEqualTo(SocietyDummy.SOCIETY_BACK_IMAGE_1);
        Assertions.assertThat(societyInfoVo.getTags()).isEqualTo(SocietyDummy.SOCIETY_TAGS_1);
    }

    @Test
    void ADD_LOCK_TYPE_SOCIETY_WITHOUT_PASSCODE_THEN_OCCUR_ERROR() {
        SocietyAddParam societyAddParam = makeDummySocietyAddParam();
        societyAddParam.setJoinType(JoinType.LOCK); // join type is LOCK
        societyAddParam.setPasscode(null); // No passcode even if join type is LOCK

        assertThatThrownBy(() -> societyService.addSociety(societyAddParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ADD_SOCIETY_INVALID_PARAMETER);
    }

    @Test
    void ADD_SOCIETY_WHEN_USER_LEADER_MAX_NUM_THEN_OCCUR_ERROR() {
        SocietyAddParam societyAddParam = makeDummySocietyAddParam();

        when(societyMemberRepository.countByUserIdAndMemberType(societyAddParam.getUserId(), MemberType.LEADER)).thenReturn(6);
        assertThatThrownBy(() -> societyService.addSociety(societyAddParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ADD_SOCIETY_LEAD_LIMIT);
    }

    @Test
    void ADD_SOCIETY_WHEN_NAME_IS_NULL_THEN_OCCUR_ERROR() {
        SocietyAddParam societyAddParam = makeDummySocietyAddParam();
        societyAddParam.setName(null);

        assertThatThrownBy(() -> societyService.addSociety(societyAddParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ADD_SOCIETY_INVALID_PARAMETER);
    }

    @Test
    void ADD_SOCIETY_WHEN_JOIN_TYPE_IS_NULL_THEN_OCCUR_ERROR() {
        SocietyAddParam societyAddParam = makeDummySocietyAddParam();
        societyAddParam.setJoinType(null);

        assertThatThrownBy(() -> societyService.addSociety(societyAddParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ADD_SOCIETY_INVALID_PARAMETER);
    }

    @Test
    void ADD_SOCIETY_WHEN_INFO_IS_NULL_THEN_OCCUR_ERROR() {
        SocietyAddParam societyAddParam = makeDummySocietyAddParam();
        societyAddParam.setInfo(null);

        assertThatThrownBy(() -> societyService.addSociety(societyAddParam))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_ADD_SOCIETY_INVALID_PARAMETER);
    }

    @Test
    void MOD_SOCIETY_THEN_SOCIETY_MODIFIED() {
        Society society = makeDummySociety();

        SocietyModParam societyModParam = SocietyModParam.builder()
                .name(SocietyDummy.SOCIETY_NAME_1)
                .joinType(JoinType.PERMIT)
                .info(SocietyDummy.SOCIETY_INFO_1)
                .image(SocietyDummy.SOCIETY_IMAGE_1)
                .backImage(SocietyDummy.SOCIETY_BACK_IMAGE_1)
                .tags(SocietyDummy.SOCIETY_TAGS_1)
                .build();

        SocietyMember societyMember = SocietyMember.builder()
                .societyId(SocietyDummy.SOCIETY_MEMBER_SOCIETY_ID)
                .userId(SocietyDummy.SOCIETY_MEMBER_USER_ID)
                .memberType(MemberType.LEADER)
                .build();

        when(societyRepository.findById(anyLong())).thenReturn(Optional.of(society));
        when(societyMemberRepository.findBySocietyIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(societyMember));
        when(societyRepository.save(any(Society.class))).thenReturn(society);
        SocietyInfoVo societyInfoVo = societyService.modSociety(societyModParam);
        Assertions.assertThat(societyInfoVo.getName()).isEqualTo(SocietyDummy.SOCIETY_NAME_1);
        Assertions.assertThat(societyInfoVo.getJoinType()).isEqualTo(JoinType.PERMIT);
        Assertions.assertThat(societyInfoVo.getInfo()).isEqualTo(SocietyDummy.SOCIETY_INFO_1);
        Assertions.assertThat(societyInfoVo.getImage()).isEqualTo(SocietyDummy.SOCIETY_IMAGE_1);
        Assertions.assertThat(societyInfoVo.getBackImage()).isEqualTo(SocietyDummy.SOCIETY_BACK_IMAGE_1);
        Assertions.assertThat(societyInfoVo.getTags()).isEqualTo(SocietyDummy.SOCIETY_TAGS_1);
    }

    // 같은 JoinType이고 LOCK이며 비밀번호가 동일한 경우: 예외 발생.
    @Test
    void CHANGE_JOIN_TYPE_WHEN_SAME_TYPE_AND_LOCK_WITH_SAME_PASSCODE_THEN_ERROR() {
        Society society = makeDummySociety();
        society.setJoinType(JoinType.LOCK);
        society.setPasscode("1234");

        assertThatThrownBy(() -> societyService.changeJoinType(society, JoinType.LOCK, "1234"))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_MOD_SOCIETY);
    }

    // 같은 JoinType이고 LOCK이 아닌 경우: 변경 없음.
    @Test
    void CHANGE_JOIN_TYPE_WHEN_SAME_TYPE_AND_NOT_LOCK_THEN_NO_CHANGE() {
        Society society = makeDummySociety();
        society.setJoinType(JoinType.PERMIT);

        societyService.changeJoinType(society, JoinType.PERMIT, null);

        Assertions.assertThat(society.getJoinType()).isEqualTo(JoinType.PERMIT);
        Assertions.assertThat(society.getPasscode()).isNull();
    }

    // 새로운 JoinType이 LOCK이고 비밀번호가 없는 경우: 예외 발생.
    @Test
    void CHANGE_JOIN_TYPE_WHEN_NEW_TYPE_IS_LOCK_AND_PASSCODE_NULL_THEN_ERROR() {
        Society society = makeDummySociety();
        society.setJoinType(JoinType.FREE);

        assertThatThrownBy(() -> societyService.changeJoinType(society, JoinType.LOCK, null))
                .isInstanceOf(RestException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FAIL_MOD_SOCIETY);
    }

    // 새로운 JoinType 이 LOCK 이고 비밀번호가 제공된 경우: 성공적으로 변경.
    @Test
    void CHANGE_JOIN_TYPE_WHEN_NEW_TYPE_IS_LOCK_AND_PASSCODE_PROVIDED_THEN_SUCCESS() {
        Society society = makeDummySociety();
        society.setJoinType(JoinType.FREE);

        societyService.changeJoinType(society, JoinType.LOCK, "newPasscode");

        Assertions.assertThat(society.getJoinType()).isEqualTo(JoinType.LOCK);
        Assertions.assertThat(society.getPasscode()).isEqualTo("newPasscode");
    }

    // 새로운 JoinType이 LOCK이 아닌 경우: 비밀번호가 null로 설정됨
    @Test
    void CHANGE_JOIN_TYPE_WHEN_NEW_TYPE_IS_NOT_LOCK_THEN_PASSCODE_NULL() {
        Society society = makeDummySociety();
        society.setJoinType(JoinType.LOCK);
        society.setPasscode("1234");

        societyService.changeJoinType(society, JoinType.FREE, null);

        Assertions.assertThat(society.getJoinType()).isEqualTo(JoinType.FREE);
        Assertions.assertThat(society.getPasscode()).isNull();
    }

    @Test
    void GET_SOCIETY_LIST_THEN_RETURN_SOCIETY_LIST() {
        // given
        Society society1 = makeDummySociety();
        Society society2 = makeDummySociety2();
        when(societyRepository.findAll()).thenReturn(List.of(society1, society2));

        // when
        List<SocietyVo> societyList = societyService.getSocietyList(new SocietyListParam());

        // then
        Assertions.assertThat(societyList).isNotEmpty();
        Assertions.assertThat(societyList.size()).isEqualTo(2);

        Assertions.assertThat(societyList.get(0).getName()).isEqualTo(SocietyDummy.SOCIETY_NAME_1);
        Assertions.assertThat(societyList.get(0).getJoinType()).isEqualTo(JoinType.PERMIT);

        Assertions.assertThat(societyList.get(1).getName()).isEqualTo(SocietyDummy.SOCIETY_NAME_2);
        Assertions.assertThat(societyList.get(1).getJoinType()).isEqualTo(JoinType.FREE);
    }

    @Test
    void RETRIEVE_SOCIETY_THEN_GET_SOCIETY() {
        // given
        SocietyInfoVo summySocietyInfoVo = SocietyInfoVo.builder()
                .name(SocietyDummy.SOCIETY_NAME_1)
                .joinType(JoinType.PERMIT)
                .info(SocietyDummy.SOCIETY_INFO_1)
                .tag(TagUtils.tagsToString(SocietyDummy.SOCIETY_TAGS_1))
                .build();
        when(societyQueryRepository.findSocietyBySocietyId(anyLong())).thenReturn(summySocietyInfoVo);
        // when
        SocietyInfoVo societyInfoVo = societyService.getSocietyInfo(SocietyInfoParam.builder().societyId(anyLong()).build());

        // then
        Assertions.assertThat(societyInfoVo).isNotNull();
        Assertions.assertThat(societyInfoVo.getName()).isEqualTo(SocietyDummy.SOCIETY_NAME_1);
        Assertions.assertThat(societyInfoVo.getJoinType()).isEqualTo(JoinType.PERMIT);
        Assertions.assertThat(societyInfoVo.getInfo()).isEqualTo(SocietyDummy.SOCIETY_INFO_1);
        Assertions.assertThat(societyInfoVo.getTags()).isEqualTo(SocietyDummy.SOCIETY_TAGS_1);
    }


    @Test
    void REMOVE_SOCIETY_THEN_DELETE_SOCIETY() {
        // given
        long societyId = 1L;
        long userId = 1L;

        Society society = makeDummySociety();
        society.setId(societyId);

        SocietyMember societyMember = SocietyMember.builder()
                .societyId(societyId)
                .userId(userId)
                .memberType(MemberType.LEADER)
                .build();

        when(societyRepository.findById(societyId)).thenReturn(Optional.of(society));
        when(societyMemberRepository.findBySocietyIdAndUserId(societyId, userId)).thenReturn(Optional.of(societyMember));
        when(societyMemberRepository.removeBySocietyId(societyId)).thenReturn(List.of(societyMember));
        when(societyMemberWaitRepository.removeBySocietyId(societyId)).thenReturn(List.of());

        // when
        societyService.removeSociety(SocietyDeleteParam.builder()
                .societyId(societyId)
                .userId(userId)
                .build());

        // then
        Assertions.assertThatNoException().isThrownBy(() -> {
            societyRepository.delete(society);
        });
    }
}