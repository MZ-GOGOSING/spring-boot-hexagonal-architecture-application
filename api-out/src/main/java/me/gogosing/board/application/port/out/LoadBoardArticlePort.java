package me.gogosing.board.application.port.out;

import me.gogosing.board.domain.BoardDomainEntity;

public interface LoadBoardArticlePort {

	BoardDomainEntity loadBoardArticle(final Long id);
}
