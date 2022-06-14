package me.gogosing.board.adapter.out.persistence;

import static java.lang.Boolean.TRUE;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.out.DeleteBoardArticlePort;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.repository.BoardContentsJpaRepository;
import me.gogosing.jpa.board.repository.BoardJpaRepository;
import me.gogosing.support.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class DeleteBoardArticlePersistenceAdapter implements DeleteBoardArticlePort {

	private final BoardJpaRepository boardJpaRepository;

	private final BoardContentsJpaRepository boardContentsJpaRepository;

	@Override
	@BoardJpaTransactional
	public void deleteBoardArticle(final Long id) {
		this.deleteBoard(id);
		this.deleteBoardContents(id);
	}

	private void deleteBoard(final Long id) {
		final var boardJpaEntity = boardJpaRepository.findByBoardIdAndDeletedFalse(id)
			.orElseThrow(EntityNotFoundException::new);

		boardJpaEntity.setDeleted(TRUE);
		boardJpaEntity.setUpdateDate(LocalDateTime.now());

		boardJpaRepository.save(boardJpaEntity);
	}

	private void deleteBoardContents(final Long id) {
		final var boardContentsJpaEntity = boardContentsJpaRepository.findByBoardId(id)
			.orElseThrow(EntityNotFoundException::new);

		boardContentsJpaRepository.delete(boardContentsJpaEntity);
	}
}
