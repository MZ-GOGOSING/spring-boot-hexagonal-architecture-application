package me.gogosing.board.adapter.out.persistence;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.out.persistence.mapper.BoardAttachmentMapper;
import me.gogosing.board.application.port.out.CreateBoardAttachmentsPort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.entity.BoardAttachmentJpaEntity;
import me.gogosing.jpa.board.repository.BoardAttachmentJpaRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CreateBoardAttachmentsPersistenceAdapter implements CreateBoardAttachmentsPort {

	private final BoardAttachmentMapper boardAttachmentMapper;

	private final BoardAttachmentJpaRepository boardAttachmentJpaRepository;

	@Override
	@BoardJpaTransactional
	public List<BoardAttachmentDomainEntity> createBoardAttachments(
		final List<BoardAttachmentDomainEntity> outCommand
	) {
		if (CollectionUtils.isEmpty(outCommand)) {
			return Collections.emptyList();
		}

		List<BoardAttachmentJpaEntity> storedBoardAttachmentJpaEntities = this.saveAll(outCommand);

		return boardAttachmentMapper.mapToDomainEntities(storedBoardAttachmentJpaEntities);
	}

	private List<BoardAttachmentJpaEntity> saveAll(final List<BoardAttachmentDomainEntity> outCommand) {
		List<BoardAttachmentJpaEntity> generatedBoardAttachmentJpaEntities =
			boardAttachmentMapper.mapToJpaEntities(outCommand);

		return boardAttachmentJpaRepository.saveAll(generatedBoardAttachmentJpaEntities);
	}
}
