package net.lodgames.storage.service.temp;

import org.springframework.stereotype.Service;

import java.util.Random;

// TODO : 임시 코드 팀장님 코드 병합 후 삭제
@Service
public class TempDiamondService {
    public Long receiveDiamondByOrder(Long userId, Long quantity, String hash) {
        //계산 했다고 가정
        return 10L; //
    }
    public Long subtractDiamondByOrder(Long userId, Long quantity) {
        //계산 했다고 가정
        return 100L;
    }
}
