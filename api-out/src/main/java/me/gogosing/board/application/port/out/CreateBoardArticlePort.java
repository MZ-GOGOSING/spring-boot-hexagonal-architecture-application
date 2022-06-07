package me.gogosing.board.application.port.out;

import me.gogosing.board.domain.BoardDomainEntity;

public interface CreateBoardArticlePort {

	BoardDomainEntity createBoardArticle(final BoardDomainEntity source);
}
