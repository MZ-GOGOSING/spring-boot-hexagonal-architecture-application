package me.gogosing.board.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.gogosing.support.assertion.AssertHelper;
import me.gogosing.support.code.board.BoardCategory;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BoardDomainEntity {

	private final Long id;

	private final String title;

	private final BoardCategory category;

	private final String contents;

	private final LocalDateTime createDate;

	private final LocalDateTime updateDate;


	private BoardDomainEntity(
		final Long id,
		final String title,
		final BoardCategory category,
		final String contents,
		final LocalDateTime createDate,
		final LocalDateTime updateDate
	) {
		AssertHelper.hasText(title, "게시물 제목은 빈 문자열일 수 없습니다.");
		AssertHelper.notNull(category, "게시물 카테고리는 반드시 지정되어야 합니다.");
		AssertHelper.hasText(contents, "게시물 내용은 빈 문자열일 수 없습니다.");

		this.id = id;
		this.title = title;
		this.category = category;
		this.contents = contents;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public static BoardDomainEntity withoutId(
		final String title,
		final BoardCategory category,
		final String contents
	) {
		return BoardDomainEntity.builder()
			.title(title)
			.category(category)
			.contents(contents)
			.build();
	}

	public static BoardDomainEntity withId(
		final Long id,
		final String title,
		final BoardCategory category,
		final LocalDateTime createDate,
		final LocalDateTime updateDate,
		final String contents
	) {
		AssertHelper.isPositive(id, "게시물 아이디는 0 이하 일 수 없습니다.");
		AssertHelper.notNull(createDate, "게시물 생성일시는 null 일 수 없습니다.");
		AssertHelper.notNull(createDate, "게시물 수정일시는 null 일 수 없습니다.");

		return BoardDomainEntity.builder()
			.id(id)
			.title(title)
			.category(category)
			.createDate(createDate)
			.updateDate(updateDate)
			.contents(contents)
			.build();
	}
}
