package me.gogosing.board.application;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.GetPaginatedBoardArticleQuery;
import me.gogosing.board.application.port.in.request.query.GetPaginatedBoardArticleInQuery;
import me.gogosing.board.application.port.in.response.GetBoardArticleItemInResponse;
import me.gogosing.board.application.port.in.response.converter.GetBoardArticleItemInResponseConverter;
import me.gogosing.board.application.port.out.LoadPaginatedBoardArticlePort;
import me.gogosing.board.application.port.out.request.query.GetPaginatedBoardArticleOutQuery;
import me.gogosing.support.jta.JtaTransactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class GetPaginatedBoardArticleService implements GetPaginatedBoardArticleQuery {

	private final LoadPaginatedBoardArticlePort loadPaginatedBoardArticlePort;

	@Override
	@JtaTransactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GetBoardArticleItemInResponse> loadAll(
		final GetPaginatedBoardArticleInQuery inQuery,
		final Pageable pageable
	) {
		final var outQuery = this.convertToOutQuery(inQuery);
		final var outResponse = loadPaginatedBoardArticlePort.findAll(outQuery, pageable);

		final var inResponseConverter = new GetBoardArticleItemInResponseConverter();

		return outResponse.map(inResponseConverter::convert);
	}

	private GetPaginatedBoardArticleOutQuery convertToOutQuery(final GetPaginatedBoardArticleInQuery inQuery) {
		return GetPaginatedBoardArticleOutQuery.builder()
			.title(inQuery.getTitle())
			.category(inQuery.getCategory())
			.contents(inQuery.getContents())
			.registeredPeriod(inQuery.getRegisteredPeriod())
			.build();
	}
}
