package me.gogosing.board.application.port.in;

import javax.validation.constraints.Min;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;

public interface GetBoardArticleQuery {

	GetBoardArticleInResponse loadById(final @Min(1L) Long id);
}
