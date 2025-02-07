package net.lodgames.stamina.service;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.stamina.model.Stamina;
import net.lodgames.stamina.param.StaminaAcquireParam;
import net.lodgames.stamina.param.StaminaConsumeParam;
import net.lodgames.stamina.param.StaminaModParam;
import net.lodgames.stamina.repository.StaminaRepository;
import net.lodgames.stamina.util.StaminaMapper;
import net.lodgames.stamina.vo.StaminaAcquireVo;
import net.lodgames.stamina.vo.StaminaCalculateVo;
import net.lodgames.stamina.vo.StaminaConsumeVo;
import net.lodgames.stamina.vo.StaminaGetVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class StaminaService {
    private final StaminaRepository staminaRepository;
    private final StaminaMapper staminaMapper;

    static int RECOVERY_INTERVAL_IN_SECONDS = 60; // 스테미나 1 회복 간격
    static int MAX_STAMINA = 100;

    // 스태미나 생성
    @Transactional(rollbackFor = Exception.class)
    public void addStamina(Long userId) {
        if(staminaRepository.existsByUserId(userId)) {
            throw new RestException(ErrorCode.EXIST_STAMINA);
        }

        staminaRepository.save(Stamina.builder()
                .userId(userId)
                .currentStamina(MAX_STAMINA)
                .maxStamina(MAX_STAMINA)
                .lastRecoveryTime(LocalDateTime.now())
                .recoveryCompleteTime(LocalDateTime.now()) //처음에는 의미 없음
                .build());
    }

    // 스태미나 조회
    @Transactional(rollbackFor = Exception.class)
    public StaminaGetVo getStamina(Long userId) {
        Stamina findStamina = findUserStamina(userId);
        int currentStamina = findStamina.getCurrentStamina();
        int maxStamina = findStamina.getMaxStamina();
        LocalDateTime lastUpdate = findStamina.getLastRecoveryTime();
        LocalDateTime recoveryTime = findStamina.getRecoveryCompleteTime();
        // 자연 회복량 및 소모량 계산
        StaminaCalculateVo result = getCheckStamina(currentStamina, maxStamina, lastUpdate, recoveryTime);
        //
        findStamina.setCurrentStamina(result.getCurrentStamina());
        findStamina.setRecoveryCompleteTime(result.getRecoveryCompleteTime());
        findStamina.setLastRecoveryTime(result.getNow());
        return staminaMapper.updateStaminaToVo(findStamina);
    }

    // 내부 테스트 용도
    // 스태미나 변경
    @Transactional(rollbackFor = Exception.class)
    public void modStamina(StaminaModParam staminaModParam) {
        Stamina findStamina = findUserStamina(staminaModParam.getUserId());
        findStamina.setCurrentStamina(staminaModParam.getCurrentStamina());
        findStamina.setMaxStamina(staminaModParam.getMaxStamina());
        findStamina.setLastRecoveryTime(staminaModParam.getLastRecoveryTime());
        staminaRepository.save(findStamina);
    }

    // 내부 테스트 용도
    // 스태미나 삭제
    @Transactional(rollbackFor = Exception.class)
    public void deleteStamina(Long userId) {
        if (!staminaRepository.existsByUserId(userId)) {
            throw new RestException(ErrorCode.NOT_FOUND_STAMINA);
        }
        staminaRepository.delete(findUserStamina(userId));
    }

    // 스태미나 소모
    @Transactional(rollbackFor = Exception.class)
    public StaminaConsumeVo consumeStamina(StaminaConsumeParam staminaConsumeParam) {
        // 해당 스테미나를 찾음
        Stamina findStamina = findUserStamina(staminaConsumeParam.getUserId());
        // Data
        int staminaCharge = staminaConsumeParam.getConsumeType().getConsumeValue(); // 소모 스태미나 값
        int currentStamina = findStamina.getCurrentStamina(); // 현재 스태미나
        int maxStamina = findStamina.getMaxStamina(); // 최대 스태미나
        LocalDateTime lastUpdate = findStamina.getLastRecoveryTime();
        // 자연 회복량 및 소모량 계산
        StaminaCalculateVo result = consumeCheckStamina(staminaCharge, currentStamina, maxStamina, lastUpdate);
        //
        findStamina.setCurrentStamina(result.getCurrentStamina());
        findStamina.setRecoveryCompleteTime(result.getRecoveryCompleteTime());
        findStamina.setLastRecoveryTime(result.getNow());

        return staminaMapper.updateStaminaToConsumeVo(staminaRepository.save(findStamina));
    }

    // 스태미나 획득
    public StaminaAcquireVo acquireStamina(StaminaAcquireParam staminaAcquireParam) {
        // 해당 스테미나를 찾음
        Stamina findStamina = findUserStamina(staminaAcquireParam.getUserId());
        // Data
        int staminaCharge = staminaAcquireParam.getAcquireType().getAcquireValue(); // 획득 스태미나 값
        int currentStamina = findStamina.getCurrentStamina(); // 현재 스태미나
        int maxStamina = findStamina.getMaxStamina(); // 최대 스태미나
        LocalDateTime lastUpdate = findStamina.getLastRecoveryTime();
        // 자연 회복량 및 획득량 계산
        StaminaCalculateVo result = acquireCheckStamina(staminaCharge, currentStamina, maxStamina, lastUpdate);
        //
        findStamina.setCurrentStamina(result.getCurrentStamina());
        findStamina.setRecoveryCompleteTime(result.getRecoveryCompleteTime());
        findStamina.setLastRecoveryTime(result.getNow());

        return staminaMapper.updateStaminaToAcquireVo(staminaRepository.save(findStamina));
    }

    private StaminaCalculateVo getCheckStamina(int currentStamina,
                                               int maxStamina,
                                               LocalDateTime lastUpdate,
                                               LocalDateTime recoveryTime) {
        LocalDateTime now = LocalDateTime.now();
        currentStamina = calculateRecoveryStamina(currentStamina, maxStamina, lastUpdate, now); // 자연 회복 스테미나량

        return StaminaCalculateVo.builder()
                .currentStamina(currentStamina)
                .maxStamina(maxStamina)
                .recoveryCompleteTime(recoveryTime)
                .now(now)
                .build();
    }

    private StaminaCalculateVo consumeCheckStamina(int staminaCharge,
                                                   int currentStamina,
                                                   int maxStamina,
                                                   LocalDateTime lastUpdate) {
        staminaCharge *= -1; // 음수로 변환
        LocalDateTime now = LocalDateTime.now();
        currentStamina = calculateRecoveryStamina(currentStamina, maxStamina, lastUpdate, now); // 자연 회복 스테미나량
        int remainingSeconds = calculateRecoveryRemainSeconds(lastUpdate,now); // 자연 회복 후 남은 자투리 시간
        return calculateStamina(staminaCharge, currentStamina, maxStamina, remainingSeconds, now);
    }

    private StaminaCalculateVo acquireCheckStamina(int staminaCharge,
                                                   int currentStamina,
                                                   int maxStamina,
                                                   LocalDateTime lastUpdate) {
        LocalDateTime now = LocalDateTime.now();
        currentStamina = calculateRecoveryStamina(currentStamina, maxStamina, lastUpdate, now); // 자연 회복 스테미나량
        int remainingSeconds = calculateRecoveryRemainSeconds(lastUpdate,now); // 자연 회복 후 남은 자투리 시간
        return calculateStamina(staminaCharge, currentStamina, maxStamina, remainingSeconds, now);
    }

    private int calculateRecoveryStamina (int currentStamina,
                                          int maxStamina,
                                          LocalDateTime lastUpdate,
                                          LocalDateTime now) {
        // 회복 시간 계산
        long secondsElapsed = ChronoUnit.SECONDS.between(lastUpdate, now);
        int recoveredStamina = (int) secondsElapsed / RECOVERY_INTERVAL_IN_SECONDS; // 스태미너 회복량
        // 자연 스태미나 회복량 계산
        int recovery = Math.min(recoveredStamina, maxStamina - currentStamina);
        return currentStamina + Math.max(0, recovery); // 음수 방지
    }

    private int calculateRecoveryRemainSeconds(LocalDateTime lastUpdate,
                                               LocalDateTime now) {
        // 회복 남은 시간 계산
        long secondsElapsed = ChronoUnit.SECONDS.between(lastUpdate, now);
        return (int) secondsElapsed % RECOVERY_INTERVAL_IN_SECONDS; // 남은 시간
    }

    public StaminaCalculateVo calculateStamina(int staminaCharge,
                                               int currentStamina,
                                               int maxStamina,
                                               int remainingSeconds,
                                               LocalDateTime now){
        // 최소값 검사
        if (currentStamina + staminaCharge < 0) {
            throw new RestException(ErrorCode.NOT_ENOUGH_STAMINA);
        }

        // 스태미나 변화 적용 (최대치 초과 가능)
        currentStamina += staminaCharge;

        // 새로운 회복 완료 시간 계산
        LocalDateTime newRecoveryCompleteTime = now; //currentStamina >= maxStamina 경우
        if (currentStamina < maxStamina) {
            int remainingRecovery = (maxStamina - currentStamina) * RECOVERY_INTERVAL_IN_SECONDS - remainingSeconds;
            newRecoveryCompleteTime = now.plusSeconds(remainingRecovery);
        }

        return StaminaCalculateVo.builder()
                .currentStamina(currentStamina)
                .maxStamina(maxStamina)
                .recoveryCompleteTime(newRecoveryCompleteTime)
                .now(now)
                .build();
    }

    // 유저 스태미나 찾기
    private Stamina findUserStamina(Long userId) {
        return staminaRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_STAMINA));
    }
}

