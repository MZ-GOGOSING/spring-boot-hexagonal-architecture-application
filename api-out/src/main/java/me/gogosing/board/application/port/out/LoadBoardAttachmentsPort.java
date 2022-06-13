package me.gogosing.board.application.port.out;

import java.util.List;
import javax.validation.constraints.Min;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;

public interface LoadBoardAttachmentsPort {

	List<BoardAttachmentDomainEntity> loadBoardAttachments(final @Min(1L) Long boardId);
}
