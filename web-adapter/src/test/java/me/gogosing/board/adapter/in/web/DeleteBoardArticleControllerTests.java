package me.gogosing.board.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.DeleteBoardArticleUseCase;
import me.gogosing.support.dto.ApiResponseGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.servlet.MockMvc;

@Import(HttpEncodingAutoConfiguration.class)
@WebMvcTest(controllers = DeleteBoardArticleController.class)
@ContextConfiguration(classes = DeleteBoardArticleController.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@DisplayName("DeleteBoardArticleController Tests")
public class DeleteBoardArticleControllerTests {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<Long> deleteBoardArticleUseCaseArgumentCaptor;

    @MockBean
    private final DeleteBoardArticleUseCase deleteBoardArticleUseCase;

    @Test
    @DisplayName("")
    public void testDeleteBoardArticle() throws Exception {
        // given
        final var boardId = 1L;
        final var expectedResult = objectMapper.writeValueAsString(
            ApiResponseGenerator.success()
        );

        doNothing()
            .when(deleteBoardArticleUseCase)
            .delete(deleteBoardArticleUseCaseArgumentCaptor.capture());

        // when
        final var actualResult = mockMvc.perform(
                delete("/v1/board/{id}", boardId)
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        // then
        assertThat(actualResult).isEqualTo(expectedResult);

        assertThat(deleteBoardArticleUseCaseArgumentCaptor.getValue()).isEqualTo(boardId);
    }
}
