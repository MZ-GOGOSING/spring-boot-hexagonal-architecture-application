package me.gogosing.board.domain.ports.outside;

import javax.validation.constraints.Min;

public interface DeleteBoardAttachmentsPort {

	void deleteBoardAttachments(final @Min(1L) Long boardId);
}
