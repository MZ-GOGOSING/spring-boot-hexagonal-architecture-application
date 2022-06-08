package me.gogosing.board.adapter.in.web;

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

@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class GetBoardArticleController {

	private final GetBoardArticleQuery getBoardArticleQuery;

	@GetMapping("/{id}")
	public ApiResponse<GetBoardArticleWebResponse> getBoardArticle(final @PathVariable @Min(1L) Long id) {
		GetBoardArticleInResponse inResponse = getBoardArticleQuery.getBoardArticle(id);
		GetBoardArticleWebResponse outResponse = new GetBoardArticleWebResponseConverter().convert(inResponse);

		return ApiResponseGenerator.success(outResponse);
	}
}
