package net.lodgames.shop.bundle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.bundle.model.Bundle;
import net.lodgames.shop.bundle.param.BundleListParam;
import net.lodgames.shop.bundle.param.BundleParam;
import net.lodgames.shop.bundle.repository.BundleQueryRepository;
import net.lodgames.shop.bundle.repository.BundleRepository;
import net.lodgames.shop.bundle.util.BundleMapper;
import net.lodgames.shop.bundle.vo.BundleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BundleService {

    private final BundleQueryRepository bundleQueryRepository;
    private final BundleMapper bundleMapper;
    private final BundleRepository bundleRepository;

    @Transactional(readOnly = true)
    public List<BundleVo> getBundleList(BundleListParam bundleListParam){
        return bundleQueryRepository.getBundleList(bundleListParam, bundleListParam.of());
    }

    @Transactional(readOnly = true)
    public BundleVo getBundle(BundleParam bundleParam){
        Bundle bundle = bundleRepository.findById(bundleParam.getBundleId())
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_BUNDLE));
        return bundleMapper.updateBundleToVo(bundle);
    }
}
