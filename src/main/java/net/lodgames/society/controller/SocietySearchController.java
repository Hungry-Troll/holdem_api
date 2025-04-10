package net.lodgames.society.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.society.param.SearchSocietyListParam;
import net.lodgames.society.service.SocietySearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SocietySearchController {

    private final SocietySearchService societySearchService;

    @GetMapping("/societies/keyword")
    public ResponseEntity<?> searchSocieties(@RequestBody SearchSocietyListParam searchSocietyListParam) {
        return ResponseEntity.ok(societySearchService.searchSocieties(searchSocietyListParam));
    }

}
