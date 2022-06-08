package me.gogosing.jpa.board.request.query;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.gogosing.support.code.board.BoardCategory;
import me.gogosing.support.dto.LocalDateRangeQuery;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardPaginationJpaCondition {

	private String title;

	private BoardCategory category;

	private String contents;

	private LocalDateRangeQuery registeredPeriod;
}
