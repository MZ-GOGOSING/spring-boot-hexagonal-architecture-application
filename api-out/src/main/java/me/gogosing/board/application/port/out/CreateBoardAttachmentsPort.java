package me.gogosing.board.application.port.out;

import java.util.List;
import javax.validation.constraints.Size;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;

public interface CreateBoardAttachmentsPort {

	List<BoardAttachmentDomainEntity> saveAll(final @Size(min = 1) List<BoardAttachmentDomainEntity> attachments);
}
