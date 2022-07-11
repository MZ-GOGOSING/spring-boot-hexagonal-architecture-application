package me.gogosing.board.application;

import static org.assertj.core.api.Assertions.assertThat;

import me.gogosing.board.application.port.in.GetBoardArticleQuery;
import me.gogosing.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTestSupport
@DisplayName("GetBoardArticleServiceTests (IntegrationTest)")
public class GetBoardArticleServiceTests {

	@Autowired
	private GetBoardArticleQuery getBoardArticleQuery;

	@Test
	@DisplayName("게시물 단건 조회 테스트")
	public void testLoadById() {
		// given
		final var id = 1L;

		// when
		final var actualResult = getBoardArticleQuery.loadById(id);

		// then
		assertThat(actualResult).isNotNull();
	}
}
