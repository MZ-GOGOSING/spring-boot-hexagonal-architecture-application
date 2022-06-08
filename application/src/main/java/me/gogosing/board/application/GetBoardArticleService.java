package me.gogosing.board.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.GetBoardArticleQuery;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.GetBoardAttachmentInResponse;
import me.gogosing.board.application.port.out.LoadBoardArticlePort;
import me.gogosing.board.domain.BoardDomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class GetBoardArticleService implements GetBoardArticleQuery {

	private final LoadBoardArticlePort loadBoardArticlePort;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GetBoardArticleInResponse getBoardArticle(final Long id) {
		BoardDomainEntity boardDomainEntity = loadBoardArticlePort.loadBoardArticle(id);

		List<GetBoardAttachmentInResponse> attachmentResponseList = boardDomainEntity.getAttachments()
			.stream()
			.map(domainEntity -> GetBoardAttachmentInResponse.builder()
				.id(domainEntity.getId())
				.path(domainEntity.getPath())
				.name(domainEntity.getName())
				.build())
			.collect(Collectors.toList());

		return GetBoardArticleInResponse.builder()
			.id(boardDomainEntity.getId())
			.title(boardDomainEntity.getTitle())
			.category(boardDomainEntity.getCategory())
			.contents(boardDomainEntity.getContents())
			.createDate(boardDomainEntity.getCreateDate())
			.updateDate(boardDomainEntity.getUpdateDate())
			.attachments(attachmentResponseList)
			.build();
	}
}
