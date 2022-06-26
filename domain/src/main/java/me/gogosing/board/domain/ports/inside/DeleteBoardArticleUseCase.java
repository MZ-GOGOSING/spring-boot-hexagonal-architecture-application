package me.gogosing.board.domain.ports.inside;

import javax.validation.constraints.Min;

public interface DeleteBoardArticleUseCase {

	void deleteBoardArticle(final @Min(1L) Long id);
}
