package me.gogosing.board.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.out.persistence.mapper.BoardArticleMapper;
import me.gogosing.board.application.port.out.LoadPaginatedBoardArticlePort;
import me.gogosing.board.application.port.out.request.query.GetPaginatedBoardArticleOutQuery;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.repository.BoardJpaRepository;
import me.gogosing.jpa.board.request.query.BoardPagingJpaCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoadPaginatedBoardArticlePersistenceAdapter implements LoadPaginatedBoardArticlePort {

	private final BoardArticleMapper boardArticleMapper;

	private final BoardJpaRepository boardJpaRepository;

	@Override
	@BoardJpaTransactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BoardDomainEntity> loadPaginatedBoardArticle(
		final GetPaginatedBoardArticleOutQuery outQuery,
		final Pageable pageable
	) {
		final var jpaQuery = this.convertToJpaQuery(outQuery);

		final var paginatedJpaEntities = boardJpaRepository
			.findAllByQuery(jpaQuery, pageable);

		return paginatedJpaEntities.map(boardArticleMapper::mapToDomainEntity);
	}

	private BoardPagingJpaCondition convertToJpaQuery(final GetPaginatedBoardArticleOutQuery outQuery) {
		return BoardPagingJpaCondition.builder()
			.title(outQuery.getTitle())
			.category(outQuery.getCategory())
			.contents(outQuery.getContents())
			.registeredPeriod(outQuery.getRegisteredPeriod())
			.build();
	}
}
