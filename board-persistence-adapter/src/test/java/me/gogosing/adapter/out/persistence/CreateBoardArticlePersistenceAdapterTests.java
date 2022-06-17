package me.gogosing.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.adapter.out.persistence.CreateBoardArticlePersistenceAdapter;
import me.gogosing.board.adapter.out.persistence.mapper.BoardArticleMapper;
import me.gogosing.board.application.port.out.CreateBoardArticlePort;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.jpa.board.entity.BoardContentsJpaEntity;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import me.gogosing.jpa.board.repository.BoardContentsJpaRepository;
import me.gogosing.jpa.board.repository.BoardJpaRepository;
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
@DisplayName("CreateBoardArticlePersistenceAdapterTests Tests")
public class CreateBoardArticlePersistenceAdapterTests {

    private CreateBoardArticlePort createBoardArticlePort;

    @Captor
    private ArgumentCaptor<BoardJpaEntity> boardJpaRepositoryArgumentCaptor;

    @Captor
    private ArgumentCaptor<BoardContentsJpaEntity> boardContentsJpaRepositoryArgumentCaptor;

    private final BoardArticleMapper boardArticleMapper = new BoardArticleMapper();

    @MockBean
    private final BoardJpaRepository boardJpaRepository;

    @MockBean
    private final BoardContentsJpaRepository boardContentsJpaRepository;

    @BeforeEach
    public void init() {
        this.createBoardArticlePort = new CreateBoardArticlePersistenceAdapter(
            this.boardArticleMapper,
            this.boardJpaRepository,
            this.boardContentsJpaRepository
        );
    }

    @Test
    @DisplayName("게시물 생성 테스트")
    public void testSave() {
        // given
        final var boardId = 1L;
        final var outCommand = buildOutCommand();
        final var boardJpaRepositoryMockReturnValue = buildBoardJpaRepositoryMockReturnValue(boardId);
        final var boardContentsJpaRepositoryMockReturnValue = buildBoardContentsJpaRepositoryMockReturnValue(boardId);

        when(boardJpaRepository.save(boardJpaRepositoryArgumentCaptor.capture()))
            .thenReturn(boardJpaRepositoryMockReturnValue);

        when(boardContentsJpaRepository.save(boardContentsJpaRepositoryArgumentCaptor.capture()))
            .thenReturn(boardContentsJpaRepositoryMockReturnValue);

        // when
        final var actualResult = createBoardArticlePort.save(outCommand);

        // then
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(boardId);
        assertThat(actualResult.getTitle()).isEqualTo(outCommand.getTitle());
        assertThat(actualResult.getCategory()).isEqualTo(outCommand.getCategory());
        assertThat(actualResult.getCreateDate()).isNotNull();
        assertThat(actualResult.getUpdateDate()).isNotNull();
        assertThat(actualResult.getContents()).isEqualTo(outCommand.getContents());

        assertWith(boardJpaRepositoryArgumentCaptor.getValue(), captorValue -> {
            assertThat(captorValue.getBoardId()).isNull();
            assertThat(captorValue.getCreateDate()).isNotNull();
            assertThat(captorValue.getUpdateDate()).isNotNull();
        });

        assertWith(boardContentsJpaRepositoryArgumentCaptor.getValue(), captorValue -> {
            assertThat(captorValue.getBoardId()).isPositive();
            assertThat(captorValue.getBoardId()).isEqualTo(boardJpaRepositoryMockReturnValue.getBoardId());
            assertThat(captorValue.getBoardContents()).isEqualTo(outCommand.getContents());
        });
    }

    private BoardDomainEntity buildOutCommand() {
        return BoardDomainEntity.withoutId(
            "게시물 제목",
            BoardCategory.NORMAL,
            "게시물 내용"
        );
    }

    @SuppressWarnings("SameParameterValue")
    private BoardJpaEntity buildBoardJpaRepositoryMockReturnValue(final Long boardId) {
        final var outCommand = buildOutCommand();
        final var mockReturnValue = boardArticleMapper.mapToJpaEntity(outCommand);

        mockReturnValue.setBoardId(boardId);
        mockReturnValue.setCreateDate(LocalDateTime.now());
        mockReturnValue.setUpdateDate(LocalDateTime.now());

        return mockReturnValue;
    }

    @SuppressWarnings("SameParameterValue")
    private BoardContentsJpaEntity buildBoardContentsJpaRepositoryMockReturnValue(final Long boardId) {
        final var outCommand = buildOutCommand();
        final var mockReturnValue = boardArticleMapper.mapToContentsJpaEntity(outCommand);

        mockReturnValue.setBoardId(boardId);

        return mockReturnValue;
    }
}
