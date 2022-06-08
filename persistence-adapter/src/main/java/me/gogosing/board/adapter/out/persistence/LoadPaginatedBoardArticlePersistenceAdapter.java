package me.gogosing.board.adapter.out.persistence;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.out.LoadPaginatedBoardArticlePort;
import me.gogosing.board.application.port.out.request.query.BoardPaginationOutQuery;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.request.query.BoardPaginationJpaCondition;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import me.gogosing.jpa.board.repository.BoardJpaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
@RequiredArgsConstructor
public class LoadPaginatedBoardArticlePersistenceAdapter implements LoadPaginatedBoardArticlePort {

	private final BoardJpaRepository boardJpaRepository;

	@Override
	@BoardJpaTransactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BoardDomainEntity> loadPaginatedBoardArticle(
		final BoardPaginationOutQuery query,
		final Pageable pageable
	) {
		BoardPaginationJpaCondition jpaQuery = convertToJpaQuery(query);

		Page<BoardJpaEntity> paginatedJpaEntities = boardJpaRepository
			.findAllByQuery(jpaQuery, pageable);

		return paginatedJpaEntities.map(this::convertToDomainEntity);
	}

	private BoardPaginationJpaCondition convertToJpaQuery(final BoardPaginationOutQuery outQuery) {
		return BoardPaginationJpaCondition.builder()
			.title(outQuery.getTitle())
			.category(outQuery.getCategory())
			.contents(outQuery.getContents())
			.registeredPeriod(outQuery.getRegisteredPeriod())
			.build();
	}

	private BoardDomainEntity convertToDomainEntity(final BoardJpaEntity jpaEntity) {
		return BoardDomainEntity.withId(
			jpaEntity.getBoardId(),
			jpaEntity.getBoardTitle(),
			jpaEntity.getBoardCategory(),
			jpaEntity.getCreateDate(),
			jpaEntity.getUpdateDate(),
			StringUtils.EMPTY,
			Collections.emptyList()
		);
	}
}
