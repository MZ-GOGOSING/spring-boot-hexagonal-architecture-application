package me.gogosing.board.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.in.web.request.query.BoardPaginationWebQuery;
import me.gogosing.board.adapter.in.web.response.GetBoardArticleItemWebResponse;
import me.gogosing.board.adapter.in.web.response.converter.GetBoardArticleItemWebResponseConverter;
import me.gogosing.board.application.port.in.GetPaginatedBoardArticleQuery;
import me.gogosing.board.application.port.in.request.query.BoardPaginationInQuery;
import me.gogosing.support.dto.ApiResponse;
import me.gogosing.support.dto.ApiResponseGenerator;
import me.gogosing.support.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class GetPaginatedBoardArticleController {

	private final GetPaginatedBoardArticleQuery getPaginatedBoardArticleQuery;

	@GetMapping
	public ApiResponse<PageResponse<GetBoardArticleItemWebResponse>> getPaginatedBoard(
		final BoardPaginationWebQuery webQuery,
		final @PageableDefault(
			size = 5,
			sort = "boardId",
			direction = Direction.DESC
		) Pageable pageable
	) {
		BoardPaginationInQuery inQuery = webQuery.toInQuery();
		GetBoardArticleItemWebResponseConverter webConverter = new GetBoardArticleItemWebResponseConverter();

		Page<GetBoardArticleItemWebResponse> paginatedResult = getPaginatedBoardArticleQuery
			.getPaginatedBoardArticle(inQuery, pageable)
			.map(webConverter::convert);

		return ApiResponseGenerator.success(PageResponse.convert(paginatedResult));
	}
}
