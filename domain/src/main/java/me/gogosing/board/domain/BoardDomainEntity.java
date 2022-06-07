package me.gogosing.board.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardDomainEntity {

	private final Long id;

	private final String title;

	private final String contents;

	private final LocalDateTime createDate;

	private final LocalDateTime updateDate;

	private final List<BoardAttachmentDomainEntity> attachments;

	public static BoardDomainEntity withoutId(
		final String title,
		final String contents,
		final List<BoardAttachmentDomainEntity> attachments
	) {
		return BoardDomainEntity.builder()
			.title(title)
			.contents(contents)
			.attachments(CollectionUtils.isEmpty(attachments) ? Collections.emptyList() : attachments)
			.build();
	}

	public static BoardDomainEntity withId(
		final Long id,
		final String title,
		final LocalDateTime createDate,
		final LocalDateTime updateDate,
		final String contents,
		final List<BoardAttachmentDomainEntity> attachments
	) {
		return BoardDomainEntity.builder()
			.id(id)
			.title(title)
			.createDate(createDate)
			.updateDate(updateDate)
			.contents(contents)
			.attachments(CollectionUtils.isEmpty(attachments) ? Collections.emptyList() : attachments)
			.build();
	}
}
