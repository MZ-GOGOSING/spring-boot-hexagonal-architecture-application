package me.gogosing.board.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.gogosing.support.code.board.BoardCategory;
import org.springframework.util.CollectionUtils;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardDomainEntity {

	private final Long id;

	private final String title;

	private final BoardCategory category;

	private final String contents;

	private final LocalDateTime createDate;

	private final LocalDateTime updateDate;

	private final List<BoardAttachmentDomainEntity> attachments;

	public static BoardDomainEntity withoutId(
		final String title,
		final BoardCategory category,
		final String contents,
		final List<BoardAttachmentDomainEntity> attachments
	) {
		return BoardDomainEntity.builder()
			.title(title)
			.category(category)
			.contents(contents)
			.attachments(CollectionUtils.isEmpty(attachments) ? Collections.emptyList() : attachments)
			.build();
	}

	public static BoardDomainEntity withId(
		final Long id,
		final String title,
		final BoardCategory category,
		final LocalDateTime createDate,
		final LocalDateTime updateDate,
		final String contents,
		final List<BoardAttachmentDomainEntity> attachments
	) {
		return BoardDomainEntity.builder()
			.id(id)
			.title(title)
			.category(category)
			.createDate(createDate)
			.updateDate(updateDate)
			.contents(contents)
			.attachments(CollectionUtils.isEmpty(attachments) ? Collections.emptyList() : attachments)
			.build();
	}
}
