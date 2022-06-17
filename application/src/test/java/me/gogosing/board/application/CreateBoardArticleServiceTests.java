package me.gogosing.board.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.CreateBoardArticleUseCase;
import me.gogosing.board.application.port.in.request.command.CreateBoardArticleInCommand;
import me.gogosing.board.application.port.in.request.command.CreateBoardAttachmentInCommand;
import me.gogosing.board.application.port.out.CreateBoardArticlePort;
import me.gogosing.board.application.port.out.CreateBoardAttachmentsPort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.support.code.board.BoardCategory;
import org.apache.commons.collections4.CollectionUtils;
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
	private ArgumentCaptor<BoardDomainEntity> createBoardArticleUseCaseArgumentCaptor;

	@Captor
	private ArgumentCaptor<List<BoardAttachmentDomainEntity>> createBoardAttachmentsPortArgumentCaptor;

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
		final var boardId = 1L;
		final var inCommand = buildInCommand();
		final var createBoardArticlePortMockReturnValue = buildOutResponse(boardId, inCommand);
		final var createBoardAttachmentsPortMockReturnValue = buildOutResponse(boardId, inCommand.getAttachments());

		when(createBoardArticlePort.save(createBoardArticleUseCaseArgumentCaptor.capture()))
			.thenReturn(createBoardArticlePortMockReturnValue);

		when(createBoardAttachmentsPort.saveAll(createBoardAttachmentsPortArgumentCaptor.capture()))
			.thenReturn(createBoardAttachmentsPortMockReturnValue);

		// when
		final var actualResult = createBoardArticleUseCase.save(inCommand);

		// then
		assertThat(actualResult).isNotNull();
		assertWith(actualResult, result -> {
			assertThat(result.getId()).isEqualTo(boardId);
			assertThat(result.getTitle()).isEqualTo(inCommand.getTitle());
			assertThat(result.getCategory()).isEqualTo(inCommand.getCategory());
			assertThat(result.getCreateDate()).isNotNull();
			assertThat(result.getUpdateDate()).isNotNull();
			assertThat(result.getContents()).isEqualTo(inCommand.getContents());
		});
		assertWith(actualResult.getAttachments(), result -> {
			assertThat(result).isNotEmpty();
			assertThat(result).hasSize(inCommand.getAttachments().size());
			assertThat(result).extracting("boardId").allMatch(id -> (Long)id == boardId);
			assertThat(result).extracting("path").containsAll(
				inCommand.getAttachments()
					.stream()
					.map(CreateBoardAttachmentInCommand::getPath)
					.collect(Collectors.toList())
			);
			assertThat(result).extracting("name").containsAll(
				inCommand.getAttachments()
					.stream()
					.map(CreateBoardAttachmentInCommand::getName)
					.collect(Collectors.toList())
			);
		});

		verify(createBoardArticlePort, times(1)).save(any());
		verify(createBoardAttachmentsPort, times(1)).saveAll(any());
	}

	private CreateBoardArticleInCommand buildInCommand() {
	 	final var attachments = List.of(
			 CreateBoardAttachmentInCommand.builder()
				.path("http://localhost:8080/foo/bar")
				.name("file1.txt")
				.build(),
			CreateBoardAttachmentInCommand.builder()
				.path("http://localhost:8080/foo/bar")
				.name("file2.txt")
				.build()
		);
		return CreateBoardArticleInCommand.builder()
			.title("게시물 제목")
			.category(BoardCategory.NORMAL)
			.contents("게시물 내용")
			.attachments(attachments)
			.build();
	}

	@SuppressWarnings("SameParameterValue")
	private BoardDomainEntity buildOutResponse(
		final Long id,
		final CreateBoardArticleInCommand inCommand
	) {
		return BoardDomainEntity.withId(
			id,
			inCommand.getTitle(),
			inCommand.getCategory(),
			LocalDateTime.now(),
			LocalDateTime.now(),
			inCommand.getContents()
		);
	}

	@SuppressWarnings("SameParameterValue")
	private List<BoardAttachmentDomainEntity> buildOutResponse(
		final Long boardId,
		final List<CreateBoardAttachmentInCommand> inCommand
	) {
		return CollectionUtils.emptyIfNull(inCommand)
			.stream()
			.map(attachment -> BoardAttachmentDomainEntity.withoutId(
				boardId,
				attachment.getPath(),
				attachment.getName()
			))
			.collect(Collectors.toList());
	}
}
