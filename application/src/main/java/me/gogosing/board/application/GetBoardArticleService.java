package me.gogosing.board.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.domain.ports.inside.GetBoardArticleQuery;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.GetBoardAttachmentInResponse;
import me.gogosing.board.domain.ports.outside.LoadBoardArticlePort;
import me.gogosing.board.domain.ports.outside.LoadBoardAttachmentsPort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
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
	public GetBoardArticleInResponse getBoardArticle(final Long id) {
		final var boardDomainEntity = loadBoardArticlePort.loadBoardArticle(id);
		final var boardAttachmentDomainEntities =
			loadBoardAttachmentsPort.loadBoardAttachments(id);

		return this.convertToInResponse(boardDomainEntity, boardAttachmentDomainEntities);
	}

	private GetBoardArticleInResponse convertToInResponse(
		final BoardDomainEntity boardArticleOutResponse,
		final List<BoardAttachmentDomainEntity> boardAttachmentOutResponse
	) {
		final var attachmentResponseList = boardAttachmentOutResponse
			.stream()
			.map(domainEntity -> GetBoardAttachmentInResponse.builder()
				.id(domainEntity.getId())
				.boardId(domainEntity.getBoardId())
				.path(domainEntity.getPath())
				.name(domainEntity.getName())
				.build())
			.collect(Collectors.toList());

		return GetBoardArticleInResponse.builder()
			.id(boardArticleOutResponse.getId())
			.title(boardArticleOutResponse.getTitle())
			.category(boardArticleOutResponse.getCategory())
			.contents(boardArticleOutResponse.getContents())
			.createDate(boardArticleOutResponse.getCreateDate())
			.updateDate(boardArticleOutResponse.getUpdateDate())
			.attachments(attachmentResponseList)
			.build();
	}
}
