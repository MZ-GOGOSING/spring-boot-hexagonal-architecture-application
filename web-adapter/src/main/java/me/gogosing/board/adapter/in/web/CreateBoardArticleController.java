package me.gogosing.board.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.in.web.request.command.CreateBoardArticleWebCommand;
import me.gogosing.board.adapter.in.web.response.CreateBoardArticleWebResponse;
import me.gogosing.board.adapter.in.web.response.converter.CreateBoardArticleWebResponseConverter;
import me.gogosing.board.application.port.in.CreateBoardArticleUseCase;
import me.gogosing.board.application.port.in.request.command.CreateBoardArticleInCommand;
import me.gogosing.board.application.port.in.response.CreateBoardArticleInResponse;
import me.gogosing.support.dto.ApiResponse;
import me.gogosing.support.dto.ApiResponseGenerator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BOARD", description = "게시물 관리 API")
@Validated
@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class CreateBoardArticleController {

	private final CreateBoardArticleUseCase createBoardArticleUseCase;

	@Operation(summary = "게시물 생성", description = "특정 게시물을 생성할 수 있습니다.")
	@PostMapping
	public ApiResponse<CreateBoardArticleWebResponse> postBoardArticle(
		final @RequestBody @Valid CreateBoardArticleWebCommand webCommand
	) {
		final var inCommand = webCommand.toInCommand();
		final var inResponse = createBoardArticleUseCase.createBoardArticle(inCommand);

		final var webResponse = new CreateBoardArticleWebResponseConverter().convert(inResponse);

		return ApiResponseGenerator.success(webResponse);
	}
}
