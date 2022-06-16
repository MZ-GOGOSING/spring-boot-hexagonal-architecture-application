package me.gogosing.board.adapter.out.persistence.mapper;

import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.jpa.board.entity.BoardContentsJpaEntity;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import org.springframework.stereotype.Component;

@Component
public final class BoardArticleMapper {

	private static final String CONTENTS_PADDING_VALUE = "*";

	public BoardDomainEntity mapToDomainEntity(final BoardJpaEntity boardJpaEntity) {
		return BoardDomainEntity.withId(
			boardJpaEntity.getBoardId(),
			boardJpaEntity.getBoardTitle(),
			boardJpaEntity.getBoardCategory(),
			boardJpaEntity.getCreateDate(),
			boardJpaEntity.getUpdateDate(),
			CONTENTS_PADDING_VALUE
		);
	}

	public BoardDomainEntity mapToDomainEntity(
		final BoardJpaEntity boardJpaEntity,
		final BoardContentsJpaEntity boardContentsJpaEntity
	) {
		return BoardDomainEntity.withId(
			boardJpaEntity.getBoardId(),
			boardJpaEntity.getBoardTitle(),
			boardJpaEntity.getBoardCategory(),
			boardJpaEntity.getCreateDate(),
			boardJpaEntity.getUpdateDate(),
			boardContentsJpaEntity.getBoardContents()
		);
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
}
