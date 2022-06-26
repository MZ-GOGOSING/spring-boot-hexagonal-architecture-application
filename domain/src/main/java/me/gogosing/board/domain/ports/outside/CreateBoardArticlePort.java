package me.gogosing.board.domain.ports.outside;

import javax.validation.constraints.NotNull;
import me.gogosing.board.domain.BoardDomainEntity;

public interface CreateBoardArticlePort {

	BoardDomainEntity createBoardArticle(final @NotNull BoardDomainEntity outCommand);
}
