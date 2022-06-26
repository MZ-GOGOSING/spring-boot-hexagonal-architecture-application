package me.gogosing.board.domain.ports.outside;

import javax.validation.constraints.Min;

public interface DeleteBoardArticlePort {

	void deleteBoardArticle(final @Min(1L) Long id);
}
