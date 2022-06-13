package me.gogosing.board.application.port.out;

import me.gogosing.board.application.port.out.request.query.GetPaginatedBoardArticleOutQuery;
import me.gogosing.board.domain.BoardDomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoadPaginatedBoardArticlePort {

	Page<BoardDomainEntity> loadPaginatedBoardArticle(
		final GetPaginatedBoardArticleOutQuery outQuery,
		final Pageable pageable
	);
}
