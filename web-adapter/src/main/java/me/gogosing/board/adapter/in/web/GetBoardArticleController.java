package me.gogosing.board.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.in.web.response.GetBoardArticleWebResponse;
import me.gogosing.board.adapter.in.web.response.converter.GetBoardArticleWebResponseConverter;
import me.gogosing.board.application.port.in.GetBoardArticleQuery;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;
import me.gogosing.support.dto.ApiResponse;
import me.gogosing.support.dto.ApiResponseGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BOARD", description = "게시물 관리 API")
@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class GetBoardArticleController {

	private final GetBoardArticleQuery getBoardArticleQuery;

	@Operation(summary = "특정 게시물 조회", description = "특정 게시물을 조회할 수 있습니다.")
	@GetMapping("/{id}")
	public ApiResponse<GetBoardArticleWebResponse> getBoardArticle(
		@Parameter(description = "게시물 식별자")
		final @PathVariable @Min(1L) Long id
	) {
		GetBoardArticleInResponse inResponse = getBoardArticleQuery.getBoardArticle(id);
		GetBoardArticleWebResponse webResponse = new GetBoardArticleWebResponseConverter().convert(inResponse);

		return ApiResponseGenerator.success(webResponse);
	}
}
