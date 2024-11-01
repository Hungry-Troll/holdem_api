package net.lodgames.shop.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.item.model.ItemUnit;
import net.lodgames.shop.item.param.ItemUnitListParam;
import net.lodgames.shop.item.param.ItemUnitParam;
import net.lodgames.shop.item.repository.ItemUnitQueryRepository;
import net.lodgames.shop.item.repository.ItemUnitRepository;
import net.lodgames.shop.item.util.ItemUnitMapper;
import net.lodgames.shop.item.vo.ItemUnitVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemUnitService {

    private final ItemUnitQueryRepository itemUnitQueryRepository;
    private final ItemUnitMapper itemUnitMapper;
    private final ItemUnitRepository itemUnitRepository;

    @Transactional(readOnly = true)
    public List<ItemUnitVo> getItemUnitList(ItemUnitListParam itemUnitListParam){
        return itemUnitQueryRepository.getItemUnitList(itemUnitListParam, itemUnitListParam.of());
    }

    @Transactional(readOnly = true)
    public ItemUnitVo getItemUnit(ItemUnitParam itemUnitParam){
        ItemUnit itemUnit = itemUnitRepository.findById(itemUnitParam.getItemUnitId())
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_ITEM_UNIT));
        return itemUnitMapper.updateItemUnitToVo(itemUnit);
    }
}
