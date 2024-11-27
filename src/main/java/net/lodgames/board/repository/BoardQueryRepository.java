package net.lodgames.board.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.board.constants.BoardStatus;
import net.lodgames.board.param.BoardSearchParam;
import net.lodgames.board.vo.BoardVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static net.lodgames.board.model.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    //게시판 리스트 조회
    public List<BoardVo> getBoardList(BoardSearchParam boardSearchParam) {
        return jpaQueryFactory.select(Projections.fields(BoardVo.class,
                        board.id,
                        board.title,
                        board.content,
                        board.boardType,
                        board.status,
                        board.image,
                        board.createdAt,
                        board.updatedAt))
                .from(board)
                .where(
                        board.status.eq(BoardStatus.PUBLISH),
                        board.boardType.eq(boardSearchParam.getBoardType())
                )
                .orderBy(board.createdAt.desc())
                .fetch();
    }
}
