package me.gogosing.board.application.port.out;

import javax.validation.constraints.Min;

public interface DeleteBoardArticlePort {

	void deleteBoardArticle(final @Min(1L) Long id);
}
