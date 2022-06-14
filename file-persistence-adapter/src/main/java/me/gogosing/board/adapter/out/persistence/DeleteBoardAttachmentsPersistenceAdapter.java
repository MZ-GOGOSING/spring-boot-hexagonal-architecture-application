package me.gogosing.board.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.out.DeleteBoardAttachmentsPort;
import me.gogosing.jpa.file.config.FileJpaTransactional;
import me.gogosing.jpa.file.repository.BoardAttachmentJpaRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class DeleteBoardAttachmentsPersistenceAdapter implements DeleteBoardAttachmentsPort {

	private final BoardAttachmentJpaRepository boardAttachmentJpaRepository;

	@Override
	@FileJpaTransactional
	public void deleteBoardAttachments(final Long boardId) {
		final var boardAttachmentJpaEntities =
			boardAttachmentJpaRepository.findAllByBoardId(boardId);

		if (CollectionUtils.isEmpty(boardAttachmentJpaEntities)) {
			return;
		}

		boardAttachmentJpaRepository.deleteAll(boardAttachmentJpaEntities);
	}
}
