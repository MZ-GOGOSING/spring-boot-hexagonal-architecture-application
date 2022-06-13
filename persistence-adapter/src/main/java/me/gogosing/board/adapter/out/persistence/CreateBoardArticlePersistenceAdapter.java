package me.gogosing.board.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.out.persistence.mapper.BoardArticleMapper;
import me.gogosing.board.application.port.out.CreateBoardArticlePort;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.entity.BoardAttachmentJpaEntity;
import me.gogosing.jpa.board.entity.BoardContentsJpaEntity;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import me.gogosing.jpa.board.repository.BoardAttachmentJpaRepository;
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

	private final BoardAttachmentJpaRepository boardAttachmentJpaRepository;

	@Override
	@BoardJpaTransactional
	public BoardDomainEntity createBoardArticle(final BoardDomainEntity outCommand) {
		BoardJpaEntity boardJpaEntity = boardArticleMapper.mapToJpaEntity(outCommand);

		boardJpaEntity.setCreateDate(LocalDateTime.now());
		boardJpaEntity.setUpdateDate(LocalDateTime.now());

		BoardContentsJpaEntity boardContentsJpaEntity = boardArticleMapper
			.mapToContentsJpaEntity(outCommand);

		List<BoardAttachmentJpaEntity> boardAttachmentJpaEntities = boardArticleMapper
			.mapToAttachmentJpaEntities(outCommand);

		boardJpaRepository.save(boardJpaEntity);

		boardContentsJpaEntity.setBoardId(boardJpaEntity.getBoardId());
		boardAttachmentJpaEntities.forEach(entity -> entity.setBoardId(boardJpaEntity.getBoardId()));

		boardContentsJpaRepository.save(boardContentsJpaEntity);
		boardAttachmentJpaRepository.saveAll(boardAttachmentJpaEntities);

		return boardArticleMapper.mapToDomainEntity(
			boardJpaEntity,
			boardContentsJpaEntity,
			boardAttachmentJpaEntities
		);
	}
}
