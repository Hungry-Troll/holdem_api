package net.lodgames.stuff.controller;

import net.lodgames.stuff.param.StuffAddParam;
import net.lodgames.stuff.param.StuffDelParam;
import net.lodgames.stuff.param.StuffListParam;
import net.lodgames.stuff.param.StuffModParam;
import net.lodgames.stuff.service.StuffService;
import net.lodgames.stuff.vo.StuffVo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/stuffs")
public class StuffController {

    private final StuffService stuffService;

    @GetMapping("/{stuffId}")
    public ResponseEntity<?> getStuff(@PathVariable("stuffId") Long stuffId) {
        return ResponseEntity.ok(stuffService.getStuff(stuffId));
    }

    // 추가
    @PostMapping("")
    public ResponseEntity<?> addStuff(@RequestBody StuffAddParam stuffAddParam) {
        StuffVo stuffVo = stuffService.addStuff(stuffAddParam);
        return ResponseEntity.ok(stuffVo);
    }

    // 변경
    @PutMapping("/{stuffId}")
    public ResponseEntity<?> modStuff(@PathVariable("stuffId") Long stuffId,@RequestBody StuffModParam stuffModParam) {
        stuffModParam.setId(stuffId);
        return ResponseEntity.ok(stuffService.modStuff(stuffModParam));
    }

    // 리스트
    @GetMapping("")
    public ResponseEntity<List<?>> getStuffList( @RequestBody StuffListParam stuffListParam) {
        List<StuffVo> stuffVoList = stuffService.getStuffList(stuffListParam);
        return ResponseEntity.ok(stuffVoList);
    }

    // 삭제
    @DeleteMapping("/{stuffId}")
    public ResponseEntity<?> deleteStuff(@PathVariable("stuffId") Long stuffId) {
        stuffService.deleteStuff(StuffDelParam.builder().id(stuffId).build());
        return ResponseEntity.ok().build();
    }

}