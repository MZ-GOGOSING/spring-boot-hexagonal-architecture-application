package me.gogosing.board.application.port.in;

import javax.validation.Valid;
import me.gogosing.board.application.port.in.request.command.CreateBoardArticleInCommand;
import me.gogosing.board.application.port.in.response.CreateBoardArticleInResponse;

public interface CreateBoardArticleUseCase {

	CreateBoardArticleInResponse save(final @Valid CreateBoardArticleInCommand inCommand);
}
