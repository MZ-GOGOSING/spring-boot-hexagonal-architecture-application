package me.gogosing.board.adapter.in.web;

import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.GetBoardArticleQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class GetBoardDetailController {

	private final GetBoardArticleQuery getBoardArticleQuery;

	@GetMapping("/{id}")
	public String getBoardDetail(final @PathVariable @Min(1L) Long id) {
		return getBoardArticleQuery.getBoardDetail(id);
	}
}
