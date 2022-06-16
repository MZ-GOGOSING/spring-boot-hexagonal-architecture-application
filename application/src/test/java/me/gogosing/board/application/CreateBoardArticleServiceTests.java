package me.gogosing.board.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.CreateBoardArticleUseCase;
import me.gogosing.board.application.port.in.request.command.CreateBoardArticleInCommand;
import me.gogosing.board.application.port.out.CreateBoardArticlePort;
import me.gogosing.board.application.port.out.CreateBoardAttachmentsPort;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.support.code.board.BoardCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@DisplayName("CreateBoardArticleServiceTests Tests")
public class CreateBoardArticleServiceTests {

	private CreateBoardArticleUseCase createBoardArticleUseCase;

	@Captor
	private ArgumentCaptor<BoardDomainEntity> boardOutCommandArgument;

	@MockBean
	private final CreateBoardArticlePort createBoardArticlePort;

	@MockBean
	private final CreateBoardAttachmentsPort createBoardAttachmentsPort;

	@BeforeEach
	public void init() {
		this.createBoardArticleUseCase = new CreateBoardArticleService(
			this.createBoardArticlePort,
			this.createBoardAttachmentsPort
		);
	}

	@Test
	@DisplayName("게시물 생성 테스트")
	public void testSave() {
		// given
		final var inCommand = buildInCommand();
		final var outResponse = buildOutResponse();

		when(createBoardArticlePort.save(boardOutCommandArgument.capture())).thenReturn(outResponse);

		// when
		final var actualResult = createBoardArticleUseCase.save(inCommand);

		// then
		assertThat(actualResult).isNotNull();
		assertWith(actualResult, result -> {
			assertThat(result.getId()).isEqualTo(outResponse.getId());
			assertThat(result.getTitle()).isEqualTo(outResponse.getTitle());
			assertThat(result.getCategory()).isEqualTo(outResponse.getCategory());
			assertThat(result.getCreateDate()).isEqualTo(outResponse.getCreateDate());
			assertThat(result.getUpdateDate()).isEqualTo(outResponse.getUpdateDate());
			assertThat(result.getContents()).isEqualTo(outResponse.getContents());
			assertThat(result.getAttachments()).isEqualTo(Collections.emptyList());
		});

		verify(createBoardArticlePort, times(1)).save(any());
		verify(createBoardAttachmentsPort, times(0)).saveAll(any());

		assertThat(boardOutCommandArgument.getValue().getId()).isNull();
	}

	private CreateBoardArticleInCommand buildInCommand() {
		return CreateBoardArticleInCommand.builder()
			.title("게시물")
			.category(BoardCategory.NORMAL)
			.contents("내용")
			.attachments(Collections.emptyList())
			.build();
	}

	private BoardDomainEntity buildOutResponse() {
		final var inCommand = buildInCommand();
		return BoardDomainEntity.withId(
			1L,
			inCommand.getTitle(),
			inCommand.getCategory(),
			LocalDateTime.now(),
			LocalDateTime.now(),
			inCommand.getContents()
		);
	}
}
