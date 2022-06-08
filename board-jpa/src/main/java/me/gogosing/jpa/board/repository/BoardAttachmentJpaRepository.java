package me.gogosing.jpa.board.repository;

import java.util.List;
import me.gogosing.jpa.board.entity.BoardAttachmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardAttachmentJpaRepository extends JpaRepository<BoardAttachmentJpaEntity, Long> {

	List<BoardAttachmentJpaEntity> findAllByBoardId(final Long boardId);
}
