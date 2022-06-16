package me.gogosing.board.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.in.web.request.command.CreateBoardArticleWebCommand;
import me.gogosing.board.adapter.in.web.response.CreateBoardArticleWebResponse;
import me.gogosing.board.application.port.in.CreateBoardArticleUseCase;
import me.gogosing.board.application.port.in.response.CreateBoardArticleInResponse;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.support.code.board.BoardCategory;
import me.gogosing.support.dto.ApiResponseGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.servlet.MockMvc;

@Import(HttpEncodingAutoConfiguration.class)
@WebMvcTest(controllers = CreateBoardArticleController.class)
@ContextConfiguration(classes = CreateBoardArticleController.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@DisplayName("CreateBoardArticleController Tests")
public class CreateBoardArticleControllerTests {

	private final MockMvc mockMvc;

	private final ObjectMapper objectMapper;

	@MockBean
	private final CreateBoardArticleUseCase createBoardArticleUseCase;

	@Test
	@DisplayName("게시물 생성 테스트")
	public void testPostBoardArticle() throws Exception {
		// given
		final var webCommand = buildWebCommand();
		final var inCommand = webCommand.toInCommand();
		final var inResponse = buildInResponse();
		final var webResponse = CreateBoardArticleWebResponse.of(inResponse);
		final var expectedResult = objectMapper.writeValueAsString(
			ApiResponseGenerator.success(webResponse)
		);

		when(createBoardArticleUseCase.save(inCommand)).thenReturn(inResponse);

		// when
		final var actualResult = mockMvc.perform(
			post("/v1/board")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(webCommand))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		// then
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	private CreateBoardArticleWebCommand buildWebCommand() {
		return CreateBoardArticleWebCommand.builder()
			.title("게시물")
			.category(BoardCategory.NORMAL)
			.contents("내용")
			.attachments(Collections.emptyList())
			.build();
	}

	private CreateBoardArticleInResponse buildInResponse() {
		final var webCommand = buildWebCommand();
		final var boardDomainEntity = BoardDomainEntity.withId(
			1L,
			webCommand.getTitle(),
			webCommand.getCategory(),
			LocalDateTime.now(),
			LocalDateTime.now(),
			webCommand.getContents()
		);
		return CreateBoardArticleInResponse.of(boardDomainEntity, Collections.emptyList());
	}
}
