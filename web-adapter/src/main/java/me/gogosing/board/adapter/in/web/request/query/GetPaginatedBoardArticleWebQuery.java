package me.gogosing.board.adapter.in.web.request.query;

import static me.gogosing.support.code.board.BoardCategory.NORMAL;
import static me.gogosing.support.code.board.BoardCategory.NOTICE;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gogosing.board.application.port.in.request.query.GetPaginatedBoardArticleInQuery;
import me.gogosing.support.code.board.BoardCategory;
import me.gogosing.support.dto.LocalDateRangeQuery;
import me.gogosing.support.validation.BetweenDate;
import me.gogosing.support.validation.board.BoardCategorySubset;
import org.springdoc.api.annotations.ParameterObject;

@Schema(description = "게시판 목록 조회 조건 정보 모델")
@ParameterObject
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class GetPaginatedBoardArticleWebQuery {

	@Parameter(description = "검색 제목 (like)")
	private String title;

	@Parameter(description = "검색 카테고리 (eq)")
	@BoardCategorySubset(anyOf = {NOTICE, NORMAL})
	private BoardCategory category;

	@Parameter(description = "검색 내용 (like)")
	private String contents;

	@Builder.Default
	@Valid
	@BetweenDate
	@Parameter(description = "검색 등록기간 (between)", required = true)
	private LocalDateRangeQuery registeredPeriod = new LocalDateRangeQuery();

	public GetPaginatedBoardArticleInQuery toInQuery() {
		return GetPaginatedBoardArticleInQuery.builder()
			.title(title)
			.category(category)
			.contents(contents)
			.registeredPeriod(registeredPeriod)
			.build();
	}
}
