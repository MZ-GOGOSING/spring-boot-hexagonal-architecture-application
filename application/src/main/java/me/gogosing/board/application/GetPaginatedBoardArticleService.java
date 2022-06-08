package me.gogosing.board.application;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.GetPaginatedBoardArticleQuery;
import me.gogosing.board.application.port.in.request.query.BoardPaginationInQuery;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;
import me.gogosing.board.application.port.out.LoadPaginatedBoardArticlePort;
import me.gogosing.board.application.port.out.request.query.BoardPaginationOutQuery;
import me.gogosing.board.domain.BoardDomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class GetPaginatedBoardArticleService implements GetPaginatedBoardArticleQuery {

	private final LoadPaginatedBoardArticlePort loadPaginatedBoardArticlePort;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GetBoardArticleInResponse> getPaginatedBoardArticle(
		final BoardPaginationInQuery query,
		final Pageable pageable
	) {
		BoardPaginationOutQuery jpaCondition = convertToJpaCondition(query);

		Page<BoardDomainEntity> paginatedDomainEntities = loadPaginatedBoardArticlePort
			.loadPaginatedBoardArticle(jpaCondition, pageable);

		return paginatedDomainEntities.map(this::convertToOutResponse);
	}

	private BoardPaginationOutQuery convertToJpaCondition(final BoardPaginationInQuery inQuery) {
		return BoardPaginationOutQuery.builder()
			.title(inQuery.getTitle())
			.category(inQuery.getCategory())
			.contents(inQuery.getContents())
			.registeredPeriod(inQuery.getRegisteredPeriod())
			.build();
	}

	private GetBoardArticleInResponse convertToOutResponse(final BoardDomainEntity domainEntity) {
		return GetBoardArticleInResponse.builder()
			.id(domainEntity.getId())
			.title(domainEntity.getTitle())
			.category(domainEntity.getCategory())
			.contents(domainEntity.getContents())
			.createDate(domainEntity.getCreateDate())
			.updateDate(domainEntity.getUpdateDate())
			.build();
	}
}
