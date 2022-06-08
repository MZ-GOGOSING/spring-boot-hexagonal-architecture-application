package me.gogosing.jpa.board.repository;

import java.util.Optional;
import me.gogosing.jpa.board.entity.BoardContentsJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardContentsJpaRepository extends JpaRepository<BoardContentsJpaEntity, Long> {

	Optional<BoardContentsJpaEntity> findByBoardId(final Long boardId);
}
