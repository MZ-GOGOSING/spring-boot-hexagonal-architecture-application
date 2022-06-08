package me.gogosing.jpa.board.repository;

import java.util.Optional;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardJpaRepository extends JpaRepository<BoardJpaEntity, Long>, BoardJpaRepositoryCustom {

	Optional<BoardJpaEntity> findByBoardIdAndDeletedFalse(final Long id);
}
