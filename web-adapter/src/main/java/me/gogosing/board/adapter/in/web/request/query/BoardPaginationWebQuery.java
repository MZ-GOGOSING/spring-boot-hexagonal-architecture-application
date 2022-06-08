package me.gogosing.board.adapter.in.web.request.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gogosing.board.application.port.in.request.query.BoardPaginationInQuery;
import me.gogosing.support.code.board.BoardCategory;
import me.gogosing.support.dto.LocalDateRangeQuery;
import me.gogosing.support.validation.BetweenDate;

@Getter
@Setter
@NoArgsConstructor
public class BoardPaginationWebQuery {

	private static final int DEFAULT_DATE_RANGE_LIMIT = 6;

	private static final int MAXIMUM_DATE_RANGE_LIMIT = 14;

	private String title;

	private BoardCategory category;

	private String contents;

	@BetweenDate(maximumDateRangeLimit = MAXIMUM_DATE_RANGE_LIMIT)
	private LocalDateRangeQuery registeredPeriod = new LocalDateRangeQuery(DEFAULT_DATE_RANGE_LIMIT);

	public BoardPaginationInQuery toInQuery() {
		return BoardPaginationInQuery.builder()
			.title(title)
			.category(category)
			.contents(contents)
			.registeredPeriod(registeredPeriod)
			.build();
	}
}
