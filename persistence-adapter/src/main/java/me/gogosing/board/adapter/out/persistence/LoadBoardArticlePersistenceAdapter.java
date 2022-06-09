package me.gogosing.board.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.out.LoadBoardArticlePort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.entity.BoardContentsJpaEntity;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import me.gogosing.jpa.board.repository.BoardAttachmentJpaRepository;
import me.gogosing.jpa.board.repository.BoardContentsJpaRepository;
import me.gogosing.jpa.board.repository.BoardJpaRepository;
import me.gogosing.support.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class LoadBoardArticlePersistenceAdapter implements LoadBoardArticlePort {

	private final BoardJpaRepository boardJpaRepository;

	private final BoardContentsJpaRepository boardContentsJpaRepository;

	private final BoardAttachmentJpaRepository boardAttachmentJpaRepository;

	@Override
	@BoardJpaTransactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BoardDomainEntity loadBoardArticle(final Long id) {
		BoardJpaEntity boardJpaEntity = boardJpaRepository.findByBoardIdAndDeletedFalse(id)
			.orElseThrow(EntityNotFoundException::new);

		String contents = boardContentsJpaRepository.findByBoardId(id)
			.map(BoardContentsJpaEntity::getBoardContents)
			.orElseThrow(EntityNotFoundException::new);

		List<BoardAttachmentDomainEntity> attachments = boardAttachmentJpaRepository.findAllByBoardId(id)
			.stream()
			.map(jpaEntity -> BoardAttachmentDomainEntity.withId(
				jpaEntity.getBoardAttachmentId(),
				jpaEntity.getBoardId(),
				jpaEntity.getBoardAttachmentPath(),
				jpaEntity.getBoardAttachmentName()
			))
			.collect(Collectors.toList());

		return BoardDomainEntity.withId(
			boardJpaEntity.getBoardId(),
			boardJpaEntity.getBoardTitle(),
			boardJpaEntity.getBoardCategory(),
			boardJpaEntity.getCreateDate(),
			boardJpaEntity.getUpdateDate(),
			contents,
			attachments
		);
	}
}
