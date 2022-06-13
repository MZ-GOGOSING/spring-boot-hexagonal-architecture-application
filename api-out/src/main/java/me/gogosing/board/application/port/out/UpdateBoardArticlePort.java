package me.gogosing.board.application.port.out;

import javax.validation.constraints.NotNull;
import me.gogosing.board.domain.BoardDomainEntity;

public interface UpdateBoardArticlePort {

	BoardDomainEntity updateBoardArticle(final @NotNull BoardDomainEntity outCommand);
}
