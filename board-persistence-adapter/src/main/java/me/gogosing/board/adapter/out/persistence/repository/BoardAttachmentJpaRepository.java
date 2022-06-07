package me.gogosing.board.adapter.out.persistence.repository;

import java.util.List;
import me.gogosing.board.adapter.out.persistence.entity.BoardAttachmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardAttachmentJpaRepository extends JpaRepository<BoardAttachmentJpaEntity, Long> {

	List<BoardAttachmentJpaEntity> findAllByBoardId(final Long boardId);
}
