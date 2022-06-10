package me.gogosing.board.application.port.in;

import javax.validation.constraints.NotNull;
import me.gogosing.board.application.port.in.request.command.BoardArticleCreationInCommand;
import me.gogosing.board.application.port.in.response.CreateBoardArticleInResponse;

public interface CreateBoardArticleUseCase {

	CreateBoardArticleInResponse createBoardArticle(final @NotNull BoardArticleCreationInCommand command);
}
