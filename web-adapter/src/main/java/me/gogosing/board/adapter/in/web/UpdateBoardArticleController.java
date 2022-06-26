package me.gogosing.board.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.in.web.request.command.UpdateBoardArticleWebCommand;
import me.gogosing.board.adapter.in.web.response.UpdateBoardArticleWebResponse;
import me.gogosing.board.adapter.in.web.response.converter.UpdateBoardArticleWebResponseConverter;
import me.gogosing.board.application.port.in.UpdateBoardArticleUseCase;
import me.gogosing.support.dto.ApiResponse;
import me.gogosing.support.dto.ApiResponseGenerator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BOARD", description = "게시물 관리 API")
@Validated
@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class UpdateBoardArticleController {

	private final UpdateBoardArticleUseCase updateBoardArticleUseCase;

	@Operation(summary = "특정 게시물 수정", description = "특정 게시물을 수정할 수 있습니다.")
	@PutMapping("/{id}")
	public ApiResponse<UpdateBoardArticleWebResponse> putBoardArticle(
		@Parameter(description = "게시물 식별자")
		final @PathVariable @Min(1L) Long id,
		final @RequestBody @Valid UpdateBoardArticleWebCommand webCommand
	) {
		final var inCommand = webCommand.toInCommand();
		final var inResponse = updateBoardArticleUseCase.updateBoardArticle(id, inCommand);

		final var webResponse = new UpdateBoardArticleWebResponseConverter().convert(inResponse);

		return ApiResponseGenerator.success(webResponse);
	}
}
