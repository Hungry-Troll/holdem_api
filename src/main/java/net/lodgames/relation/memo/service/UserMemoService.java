package net.lodgames.relation.memo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.relation.memo.model.UserMemo;
import net.lodgames.relation.memo.param.UserMemoAddParam;
import net.lodgames.relation.memo.param.UserMemoGetParam;
import net.lodgames.relation.memo.param.UserMemoModParam;
import net.lodgames.relation.memo.repository.UserMemoQueryRepository;
import net.lodgames.relation.memo.repository.UserMemoRepository;
import net.lodgames.relation.memo.param.UserMemoDelParam;
import net.lodgames.user.repository.UserRepository;
import net.lodgames.relation.memo.vo.UserMemoGetVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserMemoService {

    private final UserMemoRepository userMemoRepository;
    private final UserMemoQueryRepository userMemoQueryRepository;
    private final UserRepository userRepository;
    // 메모 추가
    @Transactional(rollbackFor = {Exception.class})
    public void addMemo (UserMemoAddParam userMemoAddParam) {
        // 메모할 타겟 유저가 없음
        if (!userRepository.findById(userMemoAddParam.getTargetUserId()).isPresent()) {
            throw new RestException(ErrorCode.NOT_EXIST_MEMO_TARGET_USER);
        }
        userMemoRepository.save(UserMemo.builder()
                .userId(userMemoAddParam.getUserId())
                .targetUserId(userMemoAddParam.getTargetUserId())
                .memoText(userMemoAddParam.getMemoText())
                .tag(userMemoAddParam.getTag())
                .build());
    }
    // 메모 조회
    @Transactional(readOnly = true)
    public UserMemoGetVo getMemo (UserMemoGetParam userMemoGetParam) {
        return userMemoQueryRepository.getMemo(userMemoGetParam);
    }
    // 메모 변경
    @Transactional(rollbackFor = {Exception.class})
    public void modMemo (UserMemoModParam userMemoModParam) {
        UserMemo userMemo = userMemoRepository.findByUserIdAndTargetUserId(
                                                    userMemoModParam.getUserId(),
                                                    userMemoModParam.getTargetUserId()
        ).orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_MEMO));
        userMemo.setMemoText(userMemoModParam.getMemoText());
        userMemo.setTag(userMemoModParam.getTag());
        userMemoRepository.save(userMemo);
    }
    // 메모 삭제
    @Transactional(rollbackFor = {Exception.class})
    public void delMemo (UserMemoDelParam userMemoDelParam) {
        UserMemo userMemo = userMemoRepository.findByUserIdAndTargetUserId(
                                                    userMemoDelParam.getUserId(),
                                                    userMemoDelParam.getTargetUserId()
        ).orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_MEMO));
        userMemoRepository.delete(userMemo);
    }
}
