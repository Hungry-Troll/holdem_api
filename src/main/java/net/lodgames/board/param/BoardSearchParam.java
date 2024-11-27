package net.lodgames.board.param;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.board.constants.BoardStatus;
import net.lodgames.board.constants.BoardType;
import net.lodgames.common.param.PagingParam;

import java.time.LocalDate;

@Getter
@Setter
public class BoardSearchParam {

    private BoardType boardType;
}
