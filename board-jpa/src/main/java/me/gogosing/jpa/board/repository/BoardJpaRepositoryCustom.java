package me.gogosing.jpa.board.repository;

import me.gogosing.jpa.board.request.query.BoardPaginationJpaCondition;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardJpaRepositoryCustom {

	Page<BoardJpaEntity> findAllByQuery(
		final BoardPaginationJpaCondition jpaCondition,
		final Pageable pageable
	);
}
