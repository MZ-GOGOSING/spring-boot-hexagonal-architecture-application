package me.gogosing.board.application.port.out.request.query;

import static me.gogosing.support.code.board.BoardCategory.NORMAL;
import static me.gogosing.support.code.board.BoardCategory.NOTICE;

import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.gogosing.support.code.board.BoardCategory;
import me.gogosing.support.dto.LocalDateRangeQuery;
import me.gogosing.support.validation.BetweenDate;
import me.gogosing.support.validation.board.BoardCategorySubset;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class GetPaginatedBoardArticleOutQuery {

	private final String title;

	@BoardCategorySubset(anyOf = {NOTICE, NORMAL})
	private final BoardCategory category;

	private final String contents;

	@Valid
	@BetweenDate
	private final LocalDateRangeQuery registeredPeriod;
}
