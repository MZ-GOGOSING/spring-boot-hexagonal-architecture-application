package me.gogosing.board.application.port.out;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.gogosing.board.application.port.out.request.query.GetPaginatedBoardArticleOutQuery;
import me.gogosing.board.domain.BoardDomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoadPaginatedBoardArticlePort {

	Page<BoardDomainEntity> findAll(
		final @Valid GetPaginatedBoardArticleOutQuery outQuery,
		final @NotNull Pageable pageable
	);
}
