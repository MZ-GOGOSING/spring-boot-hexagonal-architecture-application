package me.gogosing.board.adapter.out.persistence;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.out.persistence.mapper.BoardArticleMapper;
import me.gogosing.board.application.port.out.UpdateBoardArticlePort;
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
public class UpdateBoardArticlePersistenceAdapter implements UpdateBoardArticlePort {

	private final BoardArticleMapper boardArticleMapper;

	private final BoardJpaRepository boardJpaRepository;

	private final BoardContentsJpaRepository boardContentsJpaRepository;

	@Override
	@BoardJpaTransactional
	public BoardDomainEntity updateBoardArticle(final BoardDomainEntity outCommand) {
		BoardJpaEntity storedBoardJpaEntity = this.saveBoard(outCommand);
		BoardContentsJpaEntity storedBoardContentsJpaEntity = this.saveBoardContents(outCommand);

		return boardArticleMapper.mapToDomainEntity(
			storedBoardJpaEntity,
			storedBoardContentsJpaEntity
		);
	}

	private BoardJpaEntity saveBoard(final BoardDomainEntity outCommand) {
		BoardJpaEntity generatedBoardJpaEntity = boardArticleMapper.mapToJpaEntity(outCommand);

		generatedBoardJpaEntity.setUpdateDate(LocalDateTime.now());

		return boardJpaRepository.save(generatedBoardJpaEntity);
	}

	private BoardContentsJpaEntity saveBoardContents(final BoardDomainEntity outCommand) {
		BoardContentsJpaEntity generatedBoardContentsJpaEntity =
			boardArticleMapper.mapToContentsJpaEntity(outCommand);

		return boardContentsJpaRepository.save(generatedBoardContentsJpaEntity);
	}
}
