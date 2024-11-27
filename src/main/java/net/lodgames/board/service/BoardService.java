package net.lodgames.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lodgames.board.model.Board;
import net.lodgames.board.param.BoardSearchParam;
import net.lodgames.board.repository.BoardQueryRepository;
import net.lodgames.board.repository.BoardRepository;
import net.lodgames.board.util.BoardMapper;
import net.lodgames.board.vo.BoardVo;
import net.lodgames.config.error.ErrorCode;
import net.lodgames.config.error.exception.RestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

    private final BoardMapper boardMapper;

    //게시판 단일 조회
    public BoardVo getBoard(int boardId){
        Board board = retrieveBoard(boardId);
        return boardMapper.updateBoardToVo(board);
    }

    //게시판 리스트
    @Transactional(rollbackFor = {Exception.class})
    public List<BoardVo> getBoardList(BoardSearchParam boardSearchParam){
        return boardQueryRepository.getBoardList(boardSearchParam);
    }

    //board 조회
    public Board retrieveBoard(int boardId){
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RestException(ErrorCode.BOARD_NOT_EXIST));
    }
}
