package me.gogosing.board.application.port.in;

import javax.validation.constraints.Min;

public interface GetBoardArticleQuery {

	String getBoardDetail(final @Min(1L) Long id);
}
