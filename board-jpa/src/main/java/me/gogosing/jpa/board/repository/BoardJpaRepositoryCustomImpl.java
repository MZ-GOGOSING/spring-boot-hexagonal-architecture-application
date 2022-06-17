package me.gogosing.jpa.board.repository;

import static java.util.Collections.emptyList;
import static me.gogosing.support.query.QueryDslHelper.optionalWhen;

import com.querydsl.jpa.JPQLQuery;
import me.gogosing.jpa.board.config.BoardJpaRepositorySupport;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import me.gogosing.jpa.board.entity.QBoardContentsJpaEntity;
import me.gogosing.jpa.board.entity.QBoardJpaEntity;
import me.gogosing.jpa.board.request.query.BoardJpaFetchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@SuppressWarnings("unused")
public class BoardJpaRepositoryCustomImpl extends BoardJpaRepositorySupport
	implements BoardJpaRepositoryCustom {

	private static final QBoardJpaEntity Q_BOARD_JPA_ENTITY = QBoardJpaEntity.boardJpaEntity;

	private static final QBoardContentsJpaEntity Q_BOARD_CONTENTS_JPA_ENTITY = QBoardContentsJpaEntity.boardContentsJpaEntity;

	public BoardJpaRepositoryCustomImpl() {
		super(BoardJpaEntity.class);
	}

	@Override
	public Page<BoardJpaEntity> findAllByFetchQuery(
		final BoardJpaFetchQuery jpaFetchQuery,
		final Pageable pageable
	) {
		final var jpqlQuery = getDefaultFetchJpqlQuery();

		applyFetchWhereClause(jpqlQuery, jpaFetchQuery);

		final var totalCount = jpqlQuery.fetchCount();
		if (totalCount < 1L) {
			return new PageImpl<>(emptyList(), pageable, totalCount);
		}

		final var content = getQuerydsl()
			.applyPagination(pageable, jpqlQuery)
			.fetch();

		return new PageImpl<>(content, pageable, totalCount);
	}

	private JPQLQuery<BoardJpaEntity> getDefaultFetchJpqlQuery() {
		return getQuerydsl()
			.createQuery()
			.select(Q_BOARD_JPA_ENTITY)
			.from(Q_BOARD_JPA_ENTITY)
			.innerJoin(Q_BOARD_CONTENTS_JPA_ENTITY)
			.on(Q_BOARD_JPA_ENTITY.boardId.eq(Q_BOARD_CONTENTS_JPA_ENTITY.boardId));
	}

	private void applyFetchWhereClause(
		final JPQLQuery<BoardJpaEntity> jpqlQuery,
		final BoardJpaFetchQuery jpaFetchQuery
	) {
		optionalWhen(jpaFetchQuery.getTitle()).then(
			it -> jpqlQuery.where(Q_BOARD_JPA_ENTITY.boardTitle.containsIgnoreCase(it))
		);
		optionalWhen(jpaFetchQuery.getCategory()).then(
			it -> jpqlQuery.where(Q_BOARD_JPA_ENTITY.boardCategory.eq(it))
		);
		optionalWhen(jpaFetchQuery.getRegisteredPeriod()).then(
			period -> {
				optionalWhen(period.getStartDateTime()).then(
					startDateTime -> jpqlQuery.where(Q_BOARD_JPA_ENTITY.createDate.goe(startDateTime))
				);
				optionalWhen(period.getEndDateTime()).then(
					endDateTime -> jpqlQuery.where(Q_BOARD_JPA_ENTITY.createDate.loe(endDateTime))
				);
			}
		);
		optionalWhen(jpaFetchQuery.getContents()).then(
			it -> jpqlQuery.where(Q_BOARD_CONTENTS_JPA_ENTITY.boardContents.containsIgnoreCase(it))
		);
	}
}
