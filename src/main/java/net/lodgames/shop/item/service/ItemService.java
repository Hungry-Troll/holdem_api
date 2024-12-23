package net.lodgames.shop.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import net.lodgames.shop.item.model.Item;
import net.lodgames.shop.item.param.ItemListParam;
import net.lodgames.shop.item.param.ItemParam;
import net.lodgames.shop.item.repository.ItemQueryRepository;
import net.lodgames.shop.item.repository.ItemRepository;
import net.lodgames.shop.item.util.ItemMapper;
import net.lodgames.shop.item.vo.ItemVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemQueryRepository itemQueryRepository;
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    // 아이템 목록 조회
    @Transactional(readOnly = true)
    public List<ItemVo> getItemList(ItemListParam itemListParam){
        return itemQueryRepository.getItemList(itemListParam, itemListParam.of());
    }

    @Transactional(readOnly = true)
    public ItemVo getItem(ItemParam itemParam){
        Item item = itemRepository.findById(itemParam.getItemId())
                .orElseThrow(() -> new RestException(ErrorCode.NOT_FOUND_ITEM));
        return itemMapper.updateItemToVo(item);
    }
}
