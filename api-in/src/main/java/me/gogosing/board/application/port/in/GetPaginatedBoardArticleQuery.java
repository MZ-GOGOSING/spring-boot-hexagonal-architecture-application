package me.gogosing.board.application.port.in;

import javax.validation.constraints.NotNull;
import me.gogosing.board.application.port.in.request.query.BoardArticlePaginationInQuery;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetPaginatedBoardArticleQuery {

	Page<GetBoardArticleInResponse> getPaginatedBoardArticle(
		final BoardArticlePaginationInQuery query,
		final @NotNull Pageable pageable
	);
}
