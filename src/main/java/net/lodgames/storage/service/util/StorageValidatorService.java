package net.lodgames.storage.service.util;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.storage.model.Storage;
import net.lodgames.storage.repository.StorageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO 널처리 전용
@Service
@AllArgsConstructor
public class StorageValidatorService {

    private final StorageRepository storageRepository;

    // 보관함 받을 경우
    // 삭제 여부 검사
    @Transactional(rollbackFor = RestException.class)
    public Storage checkStorageDeleted(Long storageId) {
        // 보관함 id로 Storage 검색
        Storage findStorage = storageRepository.findById(storageId)
                .orElseThrow(()-> new RestException(ErrorCode.FAIL_READ_STORAGE));
        // 삭제 여부 체크
        if (findStorage.getDeletedAt() != null) {
            throw new RestException(ErrorCode.ALREADY_DELETED_STORAGE);
        }

        return findStorage;
    }

    // 삭제 여부 검사 + 아이디 매칭 검사
    @Transactional(rollbackFor = RestException.class)
    public Storage checkStorageError(Long storageId, Long userId) {
        Storage findStorage = checkStorageDeleted(storageId);

        // 보관함 id, receiverId 매칭 여부 확인
        if (!findStorage.getReceiverId().equals(userId)) {
            throw new RestException(ErrorCode.NOT_MATCH_STORAGE_AND_RECEIVER);
        }

        return findStorage;
    }
}
