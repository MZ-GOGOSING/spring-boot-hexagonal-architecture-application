package me.gogosing.board.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.DeleteBoardArticleUseCase;
import me.gogosing.support.dto.ApiResponse;
import me.gogosing.support.dto.ApiResponseGenerator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BOARD", description = "게시물 관리 API")
@Validated
@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class DeleteBoardArticleController {

	private final DeleteBoardArticleUseCase deleteBoardArticleUseCase;

	@Operation(summary = "특정 게시물 삭제", description = "특정 게시물을 삭제처리 할 수 있습니다.")
	@DeleteMapping("/{id}")
	public ApiResponse<Void> deleteBoardArticle(
		@Parameter(description = "게시물 식별자")
		final @PathVariable @Min(1L) Long id
	) {
		deleteBoardArticleUseCase.delete(id);

		return ApiResponseGenerator.success();
	}
}
