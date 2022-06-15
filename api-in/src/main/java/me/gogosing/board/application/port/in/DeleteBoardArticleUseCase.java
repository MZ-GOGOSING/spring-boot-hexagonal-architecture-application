package me.gogosing.board.application.port.in;

import javax.validation.constraints.Min;

public interface DeleteBoardArticleUseCase {

	void delete(final @Min(1L) Long id);
}
