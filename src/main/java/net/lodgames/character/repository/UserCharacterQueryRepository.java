package net.lodgames.character.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.lodgames.character.param.UserCharactersGetParam;
import net.lodgames.character.vo.UserCharactersVo;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import static net.lodgames.character.model.QUserCharacter.userCharacter;

@Repository
@RequiredArgsConstructor
public class UserCharacterQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<UserCharactersVo> getUserCharacters(UserCharactersGetParam param, Pageable pageable) {
        return jpaQueryFactory.select(Projections.fields(UserCharactersVo.class,
                userCharacter.id,
                userCharacter.userId,
                userCharacter.characterId,
                userCharacter.customiseId,
                userCharacter.level,
                userCharacter.grade,
                userCharacter.statusIndex
                )).from(userCharacter)
                .where(userCharacter.userId.eq(param.getUserId()))
                .orderBy(userCharacter.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
