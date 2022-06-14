package me.gogosing.jpa.board.repository;

import me.gogosing.jpa.board.request.query.BoardPagingJpaCondition;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardJpaRepositoryCustom {

	Page<BoardJpaEntity> findAllByQuery(
		final BoardPagingJpaCondition jpaCondition,
		final Pageable pageable
	);
}
