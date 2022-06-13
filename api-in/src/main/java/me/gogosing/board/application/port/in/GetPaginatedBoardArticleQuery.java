package me.gogosing.board.application.port.in;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.gogosing.board.application.port.in.request.query.GetPaginatedBoardArticleInQuery;
import me.gogosing.board.application.port.in.response.GetBoardArticleItemInResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetPaginatedBoardArticleQuery {

	Page<GetBoardArticleItemInResponse> getPaginatedBoardArticle(
		final @Valid GetPaginatedBoardArticleInQuery inQuery,
		final @NotNull Pageable pageable
	);
}
