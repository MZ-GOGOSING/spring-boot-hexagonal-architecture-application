package me.gogosing.board.application;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.DeleteBoardArticleUseCase;
import me.gogosing.board.application.port.out.DeleteBoardArticlePort;
import me.gogosing.board.application.port.out.DeleteBoardAttachmentsPort;
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
	public void delete(final Long id) {
		deleteBoardArticlePort.delete(id);
		deleteBoardAttachmentsPort.deleteAllByBoardId(id);
	}
}
