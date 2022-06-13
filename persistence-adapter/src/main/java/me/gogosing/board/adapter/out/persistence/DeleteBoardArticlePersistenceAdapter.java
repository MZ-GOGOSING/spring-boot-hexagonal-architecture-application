package me.gogosing.board.adapter.out.persistence;

import static java.lang.Boolean.TRUE;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.out.DeleteBoardArticlePort;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.entity.BoardAttachmentJpaEntity;
import me.gogosing.jpa.board.entity.BoardContentsJpaEntity;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import me.gogosing.jpa.board.repository.BoardAttachmentJpaRepository;
import me.gogosing.jpa.board.repository.BoardContentsJpaRepository;
import me.gogosing.jpa.board.repository.BoardJpaRepository;
import me.gogosing.support.exception.EntityNotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class DeleteBoardArticlePersistenceAdapter implements DeleteBoardArticlePort {

	private final BoardJpaRepository boardJpaRepository;

	private final BoardContentsJpaRepository boardContentsJpaRepository;

	private final BoardAttachmentJpaRepository boardAttachmentJpaRepository;

	@Override
	@BoardJpaTransactional
	public void deleteBoardArticle(final Long id) {
		BoardJpaEntity boardJpaEntity = boardJpaRepository.findByBoardIdAndDeletedFalse(id)
			.orElseThrow(EntityNotFoundException::new);

		BoardContentsJpaEntity boardContentsJpaEntity = boardContentsJpaRepository.findByBoardId(id)
			.orElseThrow(EntityNotFoundException::new);

		List<BoardAttachmentJpaEntity> boardAttachmentJpaEntities = boardAttachmentJpaRepository.findAllByBoardId(id);

		boardJpaEntity.setDeleted(TRUE);
		boardJpaEntity.setUpdateDate(LocalDateTime.now());

		boardJpaRepository.save(boardJpaEntity);
		boardContentsJpaRepository.delete(boardContentsJpaEntity);

		if (CollectionUtils.isNotEmpty(boardAttachmentJpaEntities)) {
			boardAttachmentJpaRepository.deleteAll(boardAttachmentJpaEntities);
		}
	}
}
