package me.gogosing.jpa.board.repository;

import static java.util.Collections.emptyList;
import static me.gogosing.support.query.QueryDslHelper.optionalWhen;

import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import me.gogosing.jpa.board.config.BoardJpaRepositorySupport;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import me.gogosing.jpa.board.entity.QBoardContentsJpaEntity;
import me.gogosing.jpa.board.entity.QBoardJpaEntity;
import me.gogosing.jpa.board.request.query.BoardPagingJpaCondition;
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
	public Page<BoardJpaEntity> findAllByQuery(
		final BoardPagingJpaCondition jpaCondition,
		final Pageable pageable
	) {
		JPQLQuery<BoardJpaEntity> jpqlQuery = getDefaultPaginationJpqlQuery();

		applyPaginationWhereClause(jpqlQuery, jpaCondition);

		long totalCount = jpqlQuery.fetchCount();
		if (totalCount < 1L) {
			return new PageImpl<>(emptyList(), pageable, totalCount);
		}

		List<BoardJpaEntity> content = getQuerydsl()
			.applyPagination(pageable, jpqlQuery)
			.fetch();

		return new PageImpl<>(content, pageable, totalCount);
	}

	private JPQLQuery<BoardJpaEntity> getDefaultPaginationJpqlQuery() {
		return getQuerydsl()
			.createQuery()
			.select(Q_BOARD_JPA_ENTITY)
			.from(Q_BOARD_JPA_ENTITY)
			.innerJoin(Q_BOARD_CONTENTS_JPA_ENTITY)
			.on(Q_BOARD_JPA_ENTITY.boardId.eq(Q_BOARD_CONTENTS_JPA_ENTITY.boardId));
	}

	private void applyPaginationWhereClause(
		final JPQLQuery<BoardJpaEntity> jpqlQuery,
		final BoardPagingJpaCondition jpaCondition
	) {
		optionalWhen(jpaCondition.getTitle()).then(
			it -> jpqlQuery.where(Q_BOARD_JPA_ENTITY.boardTitle.containsIgnoreCase(it))
		);
		optionalWhen(jpaCondition.getCategory()).then(
			it -> jpqlQuery.where(Q_BOARD_JPA_ENTITY.boardCategory.eq(it))
		);
		optionalWhen(jpaCondition.getRegisteredPeriod()).then(
			period -> {
				optionalWhen(period.getStartDateTime()).then(
					startDateTime -> jpqlQuery.where(Q_BOARD_JPA_ENTITY.createDate.goe(startDateTime))
				);
				optionalWhen(period.getEndDateTime()).then(
					endDateTime -> jpqlQuery.where(Q_BOARD_JPA_ENTITY.createDate.loe(endDateTime))
				);
			}
		);
		optionalWhen(jpaCondition.getContents()).then(
			it -> jpqlQuery.where(Q_BOARD_CONTENTS_JPA_ENTITY.boardContents.containsIgnoreCase(it))
		);
	}
}
