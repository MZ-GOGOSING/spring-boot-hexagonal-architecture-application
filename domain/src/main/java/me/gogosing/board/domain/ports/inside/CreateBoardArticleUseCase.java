package me.gogosing.board.domain.ports.inside;

import javax.validation.Valid;
import me.gogosing.board.application.port.in.request.command.CreateBoardArticleInCommand;
import me.gogosing.board.application.port.in.response.CreateBoardArticleInResponse;

public interface CreateBoardArticleUseCase {

	CreateBoardArticleInResponse createBoardArticle(final @Valid CreateBoardArticleInCommand inCommand);
}
