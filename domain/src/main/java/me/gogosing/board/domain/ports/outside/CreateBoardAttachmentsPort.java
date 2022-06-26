package me.gogosing.board.domain.ports.outside;

import java.util.List;
import javax.validation.constraints.Size;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;

public interface CreateBoardAttachmentsPort {

	List<BoardAttachmentDomainEntity> createBoardAttachments(final @Size(min = 1) List<BoardAttachmentDomainEntity> attachments);
}
