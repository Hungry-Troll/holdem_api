package net.lodgames.board.util;

import net.lodgames.board.model.Board;
import net.lodgames.board.vo.BoardVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BoardVo updateBoardToVo(Board board);
}
