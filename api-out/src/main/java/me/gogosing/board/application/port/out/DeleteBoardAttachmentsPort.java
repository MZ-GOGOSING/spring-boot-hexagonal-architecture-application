package me.gogosing.board.application.port.out;

import javax.validation.constraints.Min;

public interface DeleteBoardAttachmentsPort {

	void deleteAllByBoardId(final @Min(1L) Long boardId);
}
