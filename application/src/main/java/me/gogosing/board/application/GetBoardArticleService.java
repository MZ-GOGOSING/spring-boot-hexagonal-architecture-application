package me.gogosing.board.application;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.GetBoardArticleQuery;
import me.gogosing.board.application.port.out.LoadBoardArticlePort;
import me.gogosing.board.domain.BoardDomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBoardArticleService implements GetBoardArticleQuery {

	private final LoadBoardArticlePort loadBoardArticlePort;

	@Override
	public String getBoardDetail(final Long id) {
		BoardDomainEntity boardDomainEntity = loadBoardArticlePort.loadBoardArticle(id);

		return String.format("hello %d board !!", id);
	}
}
