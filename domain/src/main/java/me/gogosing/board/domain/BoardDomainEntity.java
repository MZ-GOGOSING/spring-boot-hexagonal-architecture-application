package me.gogosing.board.domain;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import me.gogosing.support.code.board.BoardCategory;
import org.springframework.util.Assert;

@Getter
@Builder(access = AccessLevel.PRIVATE)
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
		Assert.hasText(title, "게시물 제목은 빈 문자열일 수 없습니다.");
		Assert.notNull(category, "게시물 카테고리는 반드시 지정되어야 합니다.");
		Assert.hasText(contents, "게시물 내용은 빈 문자열일 수 없습니다.");

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
		Assert.state(defaultIfNull(id, LONG_ZERO) > 0, "게시물 아이디는 0 이하 일 수 없습니다.");
		Assert.notNull(createDate, "게시물 생성일시는 null 일 수 없습니다.");
		Assert.notNull(createDate, "게시물 수정일시는 null 일 수 없습니다.");

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
