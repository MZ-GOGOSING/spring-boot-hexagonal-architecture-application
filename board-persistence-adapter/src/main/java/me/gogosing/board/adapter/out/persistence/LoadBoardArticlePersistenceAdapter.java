package me.gogosing.board.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.out.persistence.config.BoardPersistenceTransactional;
import me.gogosing.board.adapter.out.persistence.entity.BoardContentsJpaEntity;
import me.gogosing.board.adapter.out.persistence.entity.BoardJpaEntity;
import me.gogosing.board.adapter.out.persistence.repository.BoardAttachmentJpaRepository;
import me.gogosing.board.adapter.out.persistence.repository.BoardContentsJpaRepository;
import me.gogosing.board.adapter.out.persistence.repository.BoardJpaRepository;
import me.gogosing.board.application.port.out.LoadBoardArticlePort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@BoardPersistenceTransactional(readOnly = true)
public class LoadBoardArticlePersistenceAdapter implements LoadBoardArticlePort {

	private final BoardJpaRepository boardJpaRepository;

	private final BoardContentsJpaRepository boardContentsJpaRepository;

	private final BoardAttachmentJpaRepository boardAttachmentJpaRepository;

	@Override
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
			boardJpaEntity.getCreateDate(),
			boardJpaEntity.getUpdateDate(),
			contents,
			attachments
		);
	}
}
