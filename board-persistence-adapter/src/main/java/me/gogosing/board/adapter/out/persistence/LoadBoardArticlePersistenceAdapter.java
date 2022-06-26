package me.gogosing.board.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.out.persistence.mapper.BoardArticleMapper;
import me.gogosing.board.domain.ports.outside.LoadBoardArticlePort;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.repository.BoardContentsJpaRepository;
import me.gogosing.jpa.board.repository.BoardJpaRepository;
import me.gogosing.support.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class LoadBoardArticlePersistenceAdapter implements LoadBoardArticlePort {

	private final BoardArticleMapper boardArticleMapper;

	private final BoardJpaRepository boardJpaRepository;

	private final BoardContentsJpaRepository boardContentsJpaRepository;

	@Override
	@BoardJpaTransactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BoardDomainEntity loadBoardArticle(final Long id) {
		final var boardJpaEntity = boardJpaRepository.findByBoardIdAndDeletedFalse(id)
			.orElseThrow(EntityNotFoundException::new);

		final var boardContentsJpaEntity = boardContentsJpaRepository.findByBoardId(id)
			.orElseThrow(EntityNotFoundException::new);

		return boardArticleMapper.mapToDomainEntity(
			boardJpaEntity,
			boardContentsJpaEntity
		);
	}
}
