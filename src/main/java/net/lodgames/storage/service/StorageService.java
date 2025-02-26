package net.lodgames.storage.service;

import lombok.AllArgsConstructor;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.storage.model.Storage;
import net.lodgames.storage.param.StorageDeleteParam;
import net.lodgames.storage.param.StorageReadParam;
import net.lodgames.storage.param.StoragesGetParam;
import net.lodgames.storage.repository.StorageQueryRepository;
import net.lodgames.storage.repository.StorageRepository;
import net.lodgames.storage.service.util.StorageValidatorService;
import net.lodgames.storage.vo.StorageReadVo;
import net.lodgames.storage.vo.StoragesGetVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;
    private final StorageQueryRepository storageQueryRepository;

    private final StorageValidatorService storageValidatorService;
    // 보관함 읽기
    @Transactional(rollbackFor = {Exception.class})
    public StorageReadVo readStorage(StorageReadParam storageReadParam) {
        // 1. 보관함 찾기
        Storage findStorage = storageRepository.findById(storageReadParam.getStorageId())
                .orElseThrow(()-> new RestException(ErrorCode.FAIL_READ_STORAGE));
        // 2. 읽었는지 확인 // 안 읽었으면 읽은 표시
        if (findStorage.getReadAt() == null) {
            findStorage.setReadAt(LocalDateTime.now());
        }
        // 3. 보관함을 해당 유저 아이디로 찾아서 조인 후 읽고 반환
        return storageQueryRepository.readStorage(storageReadParam)
                .orElseThrow(()-> new RestException(ErrorCode.FAIL_READ_STORAGE));
    }

    // 전체 보관함 조회
    @Transactional(rollbackFor = {Exception.class})
    public List<StoragesGetVo> getStorages(StoragesGetParam storagesGetParam) {
        if (storagesGetParam.getPage() == 0 || storagesGetParam.getLimit() == 0) {
            throw new RestException(ErrorCode.MISSING_REQUIRED_PARAMETER);
        }
        return storageQueryRepository.getStorages(storagesGetParam, storagesGetParam.of());
    }

    // 보관함 삭제
    @Transactional(rollbackFor = {Exception.class})
    public void deleteStorage(StorageDeleteParam storageDeleteParam) {
        // 보관함 id로 검색하고 없으면 ErrorCode.FAIL_STORAGE_NOT_FOUND
        // 삭제 여부 체크하고 없으면 ErrorCode.ALREADY_DELETED_STORAGE
        Storage findStorage =
                storageValidatorService.checkStorageDeleted(storageDeleteParam.getStorageId());

        // 삭제 처리
        findStorage.setDeletedAt(LocalDateTime.now());
        storageRepository.save(findStorage);
    }
}
