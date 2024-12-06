package net.lodgames.storage.service.temp;

import org.springframework.stereotype.Service;

// TODO : 임시 코드 팀장님 코드 병합 후 삭제
@Service
public class TempCoinService {
    public Long receiveCoinByOrder(Long userId, Long quantity, String hash) {
        // 계산 했다고 가정함
        return 10L; // 구매고유아이디 임시 리턴
    }
    public Long subtractCoinByOrder(Long userId, Long quantity, String hash) {
        // 계산 했다고 가정함
        return 100L;
    }
}
