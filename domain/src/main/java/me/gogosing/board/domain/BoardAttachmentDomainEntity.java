package me.gogosing.board.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BoardAttachmentDomainEntity {

	private final Long id;

	@EqualsAndHashCode.Include
	private final Long boardId;

	@EqualsAndHashCode.Include
	private final String path;

	@EqualsAndHashCode.Include
	private final String name;

	public static BoardAttachmentDomainEntity withoutId(
		final Long boardId,
		final String path,
		final String name
	) {
		return BoardAttachmentDomainEntity.builder()
			.boardId(boardId)
			.path(path)
			.name(name)
			.build();
	}

	public static BoardAttachmentDomainEntity withId(
		final Long id,
		final Long boardId,
		final String path,
		final String name
	) {
		return BoardAttachmentDomainEntity.builder()
			.id(id)
			.boardId(boardId)
			.path(path)
			.name(name)
			.build();
	}
}
