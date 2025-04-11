package net.lodgames.society.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.society.param.SearchSocietyListParam;
import net.lodgames.society.repository.SocietyQueryRepository;
import net.lodgames.society.util.TagUtils;
import net.lodgames.society.vo.SocietyVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SocietySearchService {

    private final SocietyQueryRepository societyQueryRepository;

    public List<SocietyVo> searchSocieties(SearchSocietyListParam searchSocietyListParam) {
        searchSocietyListParam.setKeyword(searchSocietyListParam.getKeyword().trim());
        List<SocietyVo> societyVos = societyQueryRepository.searchSocieties(searchSocietyListParam , searchSocietyListParam.of());
        for (SocietyVo societyVo : societyVos) {
            societyVo.setTags(TagUtils.getTagList(societyVo.getTag()));
        }
        return societyVos;
    }
}
