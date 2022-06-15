package me.gogosing.board.application.port.in;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import me.gogosing.board.application.port.in.request.command.UpdateBoardArticleInCommand;
import me.gogosing.board.application.port.in.response.UpdateBoardArticleInResponse;

public interface UpdateBoardArticleUseCase {

	UpdateBoardArticleInResponse save(
		final @Min(1L) Long id,
		final @Valid UpdateBoardArticleInCommand inCommand
	);
}
