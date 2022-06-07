package me.gogosing.board.adapter.out.persistence.repository;

import java.util.Optional;
import me.gogosing.board.adapter.out.persistence.entity.BoardJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardJpaRepository extends JpaRepository<BoardJpaEntity, Long> {

	Optional<BoardJpaEntity> findByBoardIdAndDeletedFalse(final Long id);
}
