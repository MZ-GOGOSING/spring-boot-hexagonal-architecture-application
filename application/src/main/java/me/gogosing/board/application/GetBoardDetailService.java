package me.gogosing.board.application;

import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.GetBoardDetailQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBoardDetailService implements GetBoardDetailQuery {

	@Override
	public String getBoardDetail(final Long id) {
		return String.format("hello %d board !!", id);
	}
}
