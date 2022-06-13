package me.gogosing.board.application.port.out;

import javax.validation.constraints.Min;

public interface DeleteBoardAttachmentsPort {

	void deleteBoardAttachments(final @Min(1L) Long boardId);
}
