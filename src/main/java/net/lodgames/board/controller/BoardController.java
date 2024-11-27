package net.lodgames.board.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.board.param.BoardSearchParam;
import net.lodgames.board.service.BoardService;
import net.lodgames.config.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class BoardController {

    private final BoardService boardService;

    //게시판 단일 조회
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable("boardId") int boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    //게시판 리스트 조회
    @GetMapping("/boards")
    public ResponseEntity<?> getBoardList(@RequestBody BoardSearchParam boardSearchParam) {
        return ResponseEntity.ok(boardService.getBoardList(boardSearchParam));
    }
}
