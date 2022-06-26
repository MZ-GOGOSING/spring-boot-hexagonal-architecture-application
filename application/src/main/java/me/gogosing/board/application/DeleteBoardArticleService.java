package me.gogosing.board.application;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.domain.ports.inside.DeleteBoardArticleUseCase;
import me.gogosing.board.domain.ports.outside.DeleteBoardArticlePort;
import me.gogosing.board.domain.ports.outside.DeleteBoardAttachmentsPort;
import me.gogosing.support.jta.JtaTransactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class DeleteBoardArticleService implements DeleteBoardArticleUseCase {

	private final DeleteBoardArticlePort deleteBoardArticlePort;

	private final DeleteBoardAttachmentsPort deleteBoardAttachmentsPort;

	@Override
	@JtaTransactional
	public void deleteBoardArticle(final Long id) {
		deleteBoardArticlePort.deleteBoardArticle(id);
		deleteBoardAttachmentsPort.deleteBoardAttachments(id);
	}
}
