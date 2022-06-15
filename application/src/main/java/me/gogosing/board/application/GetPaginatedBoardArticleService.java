package me.gogosing.board.application;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.GetPaginatedBoardArticleQuery;
import me.gogosing.board.application.port.in.request.query.GetPaginatedBoardArticleInQuery;
import me.gogosing.board.application.port.in.response.GetBoardArticleItemInResponse;
import me.gogosing.board.application.port.out.LoadPaginatedBoardArticlePort;
import me.gogosing.board.application.port.out.request.query.GetPaginatedBoardArticleOutQuery;
import me.gogosing.board.domain.BoardDomainEntity;
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
	public Page<GetBoardArticleItemInResponse> getPaginatedBoardArticle(
		final GetPaginatedBoardArticleInQuery inQuery,
		final Pageable pageable
	) {
		final var outQuery = this.convertToOutQuery(inQuery);

		final var outResponse = loadPaginatedBoardArticlePort
			.loadPaginatedBoardArticle(outQuery, pageable);

		return outResponse.map(this::convertToInResponse);
	}

	private GetPaginatedBoardArticleOutQuery convertToOutQuery(final GetPaginatedBoardArticleInQuery inQuery) {
		return GetPaginatedBoardArticleOutQuery.builder()
			.title(inQuery.getTitle())
			.category(inQuery.getCategory())
			.contents(inQuery.getContents())
			.registeredPeriod(inQuery.getRegisteredPeriod())
			.build();
	}

	private GetBoardArticleItemInResponse convertToInResponse(final BoardDomainEntity domainEntity) {
		return GetBoardArticleItemInResponse.builder()
			.id(domainEntity.getId())
			.title(domainEntity.getTitle())
			.category(domainEntity.getCategory())
			.createDate(domainEntity.getCreateDate())
			.updateDate(domainEntity.getUpdateDate())
			.build();
	}
}
