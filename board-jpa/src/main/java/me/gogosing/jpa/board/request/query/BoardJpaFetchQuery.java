package me.gogosing.jpa.board.request.query;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.gogosing.support.code.board.BoardCategory;
import me.gogosing.support.dto.LocalDateRangeQuery;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BoardJpaFetchQuery {

	private final String title;

	private final BoardCategory category;

	private final String contents;

	private final LocalDateRangeQuery registeredPeriod;
}
