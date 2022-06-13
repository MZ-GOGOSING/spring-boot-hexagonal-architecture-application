package me.gogosing.board.application;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.DeleteBoardArticleUseCase;
import me.gogosing.board.application.port.out.DeleteBoardArticlePort;
import me.gogosing.board.application.port.out.DeleteBoardAttachmentsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class DeleteBoardArticleService implements DeleteBoardArticleUseCase {

	private final DeleteBoardArticlePort deleteBoardArticlePort;

	private final DeleteBoardAttachmentsPort deleteBoardAttachmentsPort;

	@Override
	@Transactional
	public void deleteBoardArticle(final Long id) {
		deleteBoardArticlePort.deleteBoardArticle(id);
		deleteBoardAttachmentsPort.deleteBoardAttachments(id);
	}
}
