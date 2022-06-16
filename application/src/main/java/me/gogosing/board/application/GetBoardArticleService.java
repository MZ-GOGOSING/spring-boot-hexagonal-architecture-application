package me.gogosing.board.application;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.GetBoardArticleQuery;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;
import me.gogosing.board.application.port.out.LoadBoardArticlePort;
import me.gogosing.board.application.port.out.LoadBoardAttachmentsPort;
import me.gogosing.support.jta.JtaTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class GetBoardArticleService implements GetBoardArticleQuery {

	private final LoadBoardArticlePort loadBoardArticlePort;

	private final LoadBoardAttachmentsPort loadBoardAttachmentsPort;

	@Override
	@JtaTransactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GetBoardArticleInResponse loadById(final Long id) {
		final var boardDomainEntity = loadBoardArticlePort.findById(id);
		final var boardAttachmentDomainEntities =
			loadBoardAttachmentsPort.findAllByBoardId(id);

		return GetBoardArticleInResponse.of(boardDomainEntity, boardAttachmentDomainEntities);
	}
}
