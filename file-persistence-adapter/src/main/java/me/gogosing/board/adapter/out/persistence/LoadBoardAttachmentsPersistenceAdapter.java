package me.gogosing.board.adapter.out.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.out.persistence.mapper.BoardAttachmentMapper;
import me.gogosing.board.application.port.out.LoadBoardAttachmentsPort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.jpa.file.config.FileJpaTransactional;
import me.gogosing.jpa.file.repository.BoardAttachmentJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class LoadBoardAttachmentsPersistenceAdapter implements LoadBoardAttachmentsPort {

	private final BoardAttachmentMapper boardAttachmentMapper;

	private final BoardAttachmentJpaRepository boardAttachmentJpaRepository;

	@Override
	@FileJpaTransactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BoardAttachmentDomainEntity> findAllByBoardId(final Long boardId) {
		final var boardAttachmentJpaEntities =
			boardAttachmentJpaRepository.findAllByBoardId(boardId);

		return boardAttachmentMapper.mapToDomainEntities(boardAttachmentJpaEntities);
	}
}
