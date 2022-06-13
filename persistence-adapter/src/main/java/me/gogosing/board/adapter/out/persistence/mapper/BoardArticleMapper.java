package me.gogosing.board.adapter.out.persistence.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.jpa.board.entity.BoardAttachmentJpaEntity;
import me.gogosing.jpa.board.entity.BoardContentsJpaEntity;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Component
public class BoardArticleMapper {

	private static final String CONTENTS_PADDING_VALUE = "*";

	public BoardDomainEntity mapToDomainEntity(final BoardJpaEntity boardJpaEntity) {
		return BoardDomainEntity.withId(
			boardJpaEntity.getBoardId(),
			boardJpaEntity.getBoardTitle(),
			boardJpaEntity.getBoardCategory(),
			boardJpaEntity.getCreateDate(),
			boardJpaEntity.getUpdateDate(),
			CONTENTS_PADDING_VALUE,
			Collections.emptyList()
		);
	}

	public BoardDomainEntity mapToDomainEntity(
		final BoardJpaEntity boardJpaEntity,
		final BoardContentsJpaEntity boardContentsJpaEntity,
		final List<BoardAttachmentJpaEntity> boardAttachmentJpaEntities
	) {
		return BoardDomainEntity.withId(
			boardJpaEntity.getBoardId(),
			boardJpaEntity.getBoardTitle(),
			boardJpaEntity.getBoardCategory(),
			boardJpaEntity.getCreateDate(),
			boardJpaEntity.getUpdateDate(),
			boardContentsJpaEntity.getBoardContents(),
			mapToAttachmentDomainEntities(boardAttachmentJpaEntities)
		);
	}

	private List<BoardAttachmentDomainEntity> mapToAttachmentDomainEntities(
		final List<BoardAttachmentJpaEntity> boardAttachmentJpaEntities
	) {
		return CollectionUtils.emptyIfNull(boardAttachmentJpaEntities)
			.stream()
			.map(jpaEntity -> BoardAttachmentDomainEntity.withId(
				jpaEntity.getBoardAttachmentId(),
				jpaEntity.getBoardId(),
				jpaEntity.getBoardAttachmentPath(),
				jpaEntity.getBoardAttachmentName()
			))
			.collect(Collectors.toList());
	}

	public BoardJpaEntity mapToJpaEntity(final BoardDomainEntity boardDomainEntity) {
		return BoardJpaEntity.builder()
			.boardId(boardDomainEntity.getId())
			.boardTitle(boardDomainEntity.getTitle())
			.boardCategory(boardDomainEntity.getCategory())
			.createDate(boardDomainEntity.getCreateDate())
			.updateDate(boardDomainEntity.getUpdateDate())
			.build();
	}

	public BoardContentsJpaEntity mapToContentsJpaEntity(final BoardDomainEntity boardDomainEntity) {
		return BoardContentsJpaEntity.builder()
			.boardId(boardDomainEntity.getId())
			.boardContents(boardDomainEntity.getContents())
			.build();
	}

	public List<BoardAttachmentJpaEntity> mapToAttachmentJpaEntities(final BoardDomainEntity boardDomainEntity) {
		return CollectionUtils.emptyIfNull(boardDomainEntity.getAttachments())
			.stream()
			.map(attachmentDomainEntity -> this.mapToAttachmentJpaEntity(boardDomainEntity.getId(), attachmentDomainEntity))
			.collect(Collectors.toList());
	}

	private BoardAttachmentJpaEntity mapToAttachmentJpaEntity(
		final Long boardId,
		final BoardAttachmentDomainEntity boardAttachmentDomainEntity
	) {
		return BoardAttachmentJpaEntity.builder()
			.boardId(boardId)
			.boardAttachmentPath(boardAttachmentDomainEntity.getPath())
			.boardAttachmentName(boardAttachmentDomainEntity.getName())
			.build();
	}
}
