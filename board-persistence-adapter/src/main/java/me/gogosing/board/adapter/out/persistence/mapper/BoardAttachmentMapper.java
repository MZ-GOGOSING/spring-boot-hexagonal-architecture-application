package me.gogosing.board.adapter.out.persistence.mapper;

import java.util.List;
import java.util.stream.Collectors;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.jpa.board.entity.BoardAttachmentJpaEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Component
public class BoardAttachmentMapper {

	public BoardAttachmentDomainEntity mapToDomainEntity(
		final BoardAttachmentJpaEntity boardAttachmentJpaEntity
	) {
		return BoardAttachmentDomainEntity.withId(
			boardAttachmentJpaEntity.getBoardAttachmentId(),
			boardAttachmentJpaEntity.getBoardId(),
			boardAttachmentJpaEntity.getBoardAttachmentPath(),
			boardAttachmentJpaEntity.getBoardAttachmentName()
		);
	}

	public List<BoardAttachmentDomainEntity> mapToDomainEntities(
		final List<BoardAttachmentJpaEntity> boardAttachmentJpaEntities
	) {
		return CollectionUtils.emptyIfNull(boardAttachmentJpaEntities)
			.stream()
			.map(this::mapToDomainEntity)
			.distinct()
			.collect(Collectors.toList());
	}

	public BoardAttachmentJpaEntity mapToJpaEntity(
		final BoardAttachmentDomainEntity boardAttachmentDomainEntity
	) {
		return BoardAttachmentJpaEntity.builder()
			.boardAttachmentId(boardAttachmentDomainEntity.getId())
			.boardId(boardAttachmentDomainEntity.getBoardId())
			.boardAttachmentPath(boardAttachmentDomainEntity.getPath())
			.boardAttachmentName(boardAttachmentDomainEntity.getName())
			.build();
	}

	public List<BoardAttachmentJpaEntity> mapToJpaEntities(
		final List<BoardAttachmentDomainEntity> boardAttachmentDomainEntities
	) {
		return CollectionUtils.emptyIfNull(boardAttachmentDomainEntities)
			.stream()
			.map(this::mapToJpaEntity)
			.collect(Collectors.toList());
	}
}
