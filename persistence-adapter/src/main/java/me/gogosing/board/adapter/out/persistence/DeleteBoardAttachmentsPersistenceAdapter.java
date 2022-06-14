package me.gogosing.board.adapter.out.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.out.DeleteBoardAttachmentsPort;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.entity.BoardAttachmentJpaEntity;
import me.gogosing.jpa.board.repository.BoardAttachmentJpaRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class DeleteBoardAttachmentsPersistenceAdapter implements DeleteBoardAttachmentsPort {

	private final BoardAttachmentJpaRepository boardAttachmentJpaRepository;

	@Override
	@BoardJpaTransactional
	public void deleteBoardAttachments(final Long boardId) {
		List<BoardAttachmentJpaEntity> boardAttachmentJpaEntities =
			boardAttachmentJpaRepository.findAllByBoardId(boardId);

		if (CollectionUtils.isEmpty(boardAttachmentJpaEntities)) {
			return;
		}

		boardAttachmentJpaRepository.deleteAll(boardAttachmentJpaEntities);
	}
}
