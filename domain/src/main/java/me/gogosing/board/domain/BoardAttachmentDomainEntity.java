package me.gogosing.board.domain;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BoardAttachmentDomainEntity {

	private final Long id;

	@EqualsAndHashCode.Include
	private final Long boardId;

	@EqualsAndHashCode.Include
	private final String path;

	@EqualsAndHashCode.Include
	private final String name;

	private BoardAttachmentDomainEntity(
		final Long id,
		final Long boardId,
		final String path,
		final String name
	) {
		Assert.state(defaultIfNull(boardId, LONG_ZERO) > 0, "소속 게시물 아이디는 0 이하 일 수 없습니다.");
		Assert.hasText(path, "첨부파일 경로는 반드시 존재하여야 합니다.");
		Assert.hasText(path, "첨부파일 파일명은 반드시 존재하여야 합니다.");

		this.id = id;
		this.boardId = boardId;
		this.path = path;
		this.name = name;
	}

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
		Assert.state(defaultIfNull(id, LONG_ZERO) > 0, "첨부파일 아이디는 0 이하 일 수 없습니다.");

		return BoardAttachmentDomainEntity.builder()
			.id(id)
			.boardId(boardId)
			.path(path)
			.name(name)
			.build();
	}
}
