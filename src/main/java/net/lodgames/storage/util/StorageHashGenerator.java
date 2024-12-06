package net.lodgames.storage.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;

@Component
public class StorageHashGenerator {
    public String generateMD5(Long storageId) {
        try {
            // 인스턴스 생성
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 입력 문자열의 바이트 배열을 해시 계산
            byte[] hashInBytes = md.digest(storageId.toString().getBytes());
            // 바이트 배열을 16진수 문자열로 변환
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
