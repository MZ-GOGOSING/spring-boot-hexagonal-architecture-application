package me.gogosing.board.domain.ports.inside;

import javax.validation.constraints.Min;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;

public interface GetBoardArticleQuery {

	GetBoardArticleInResponse getBoardArticle(final @Min(1L) Long id);
}
