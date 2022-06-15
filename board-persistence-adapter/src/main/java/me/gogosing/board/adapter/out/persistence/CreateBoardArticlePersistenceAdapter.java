package me.gogosing.board.adapter.out.persistence;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.out.persistence.mapper.BoardArticleMapper;
import me.gogosing.board.application.port.out.CreateBoardArticlePort;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.entity.BoardContentsJpaEntity;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import me.gogosing.jpa.board.repository.BoardContentsJpaRepository;
import me.gogosing.jpa.board.repository.BoardJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CreateBoardArticlePersistenceAdapter implements CreateBoardArticlePort {

	private final BoardArticleMapper boardArticleMapper;

	private final BoardJpaRepository boardJpaRepository;

	private final BoardContentsJpaRepository boardContentsJpaRepository;

	@Override
	@BoardJpaTransactional
	public BoardDomainEntity save(final BoardDomainEntity outCommand) {
		final var storedBoardJpaEntity = this.saveBoard(outCommand);
		final var storedBoardContentsJpaEntity =
			this.saveBoardContents(storedBoardJpaEntity.getBoardId(), outCommand);

		return boardArticleMapper.mapToDomainEntity(
			storedBoardJpaEntity,
			storedBoardContentsJpaEntity
		);
	}

	private BoardJpaEntity saveBoard(final BoardDomainEntity outCommand) {
		final var generatedBoardJpaEntity = boardArticleMapper.mapToJpaEntity(outCommand);

		generatedBoardJpaEntity.setCreateDate(LocalDateTime.now());
		generatedBoardJpaEntity.setUpdateDate(LocalDateTime.now());

		return boardJpaRepository.save(generatedBoardJpaEntity);
	}

	private BoardContentsJpaEntity saveBoardContents(
		final Long boardId,
		final BoardDomainEntity outCommand
	) {
		final var generatedBoardContentsJpaEntity =
			boardArticleMapper.mapToContentsJpaEntity(outCommand);

		generatedBoardContentsJpaEntity.setBoardId(boardId);

		return boardContentsJpaRepository.save(generatedBoardContentsJpaEntity);
	}
}
