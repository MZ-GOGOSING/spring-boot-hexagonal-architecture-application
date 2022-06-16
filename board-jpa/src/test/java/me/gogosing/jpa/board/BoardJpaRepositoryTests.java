package me.gogosing.jpa.board;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.gogosing.jpa.board.entity.BoardJpaEntity;
import me.gogosing.jpa.board.repository.BoardJpaRepository;
import me.gogosing.jpa.board.request.query.BoardPagingJpaCondition;
import me.gogosing.jpa.support.QueryDslRepositoryTest;
import me.gogosing.support.code.board.BoardCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@QueryDslRepositoryTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@DisplayName("BoardJpaRepository Tests")
public class BoardJpaRepositoryTests {

	private final BoardJpaRepository boardJpaRepository;

	@Test
	@DisplayName("BoardJpaEntity 프로퍼티 존재 여부 확인")
	public void testHasProperties() {
		final var boardJpaEntity = new BoardJpaEntity();
		assertThat(boardJpaEntity)
			.hasFieldOrProperty("boardId")
			.hasFieldOrProperty("boardTitle")
			.hasFieldOrProperty("boardCategory")
			.hasFieldOrProperty("deleted")
			.hasFieldOrProperty("createDate")
			.hasFieldOrProperty("updateDate");
	}

	@Test
	@DisplayName("BoardJpaEntity 저장 동작 여부 확인")
	public void testSave() {
		// given
		final var boardJpaEntity = BoardJpaEntity.builder()
			.boardTitle("테스트 게시물 제목")
			.boardCategory(BoardCategory.NORMAL)
			.deleted(true)
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.build();

		// when
		final var storedJpaEntity = boardJpaRepository.save(boardJpaEntity);

		// then
		assertWith(storedJpaEntity, entity -> {
			assertThat(entity).isNotNull();
			assertThat(entity.getBoardId()).isPositive();
		});
	}

	@Test
	@DisplayName("BoardJpaEntity 페이징 정상 동작 여부 확인")
	public void testFindAllByQuery() {
		// given
		final var jpaCondition = BoardPagingJpaCondition.builder()
			.title("첫번째")
			.build();

		final var pageable = PageRequest
			.of(0, 10, Sort.by(Order.asc("boardId")));

		// when
		final var paginatedResult = boardJpaRepository.findAllByQuery(jpaCondition, pageable);

		// then
		assertThat(paginatedResult).isNotEmpty();
		assertWith(paginatedResult, result -> {
			assertThat(result.getTotalElements()).isNotZero();
			assertThat(result.getTotalElements()).isEqualTo(LONG_ONE);
			assertThat(result.getContent())
				.extracting("boardTitle")
				.contains("첫번째 게시물 제목");
		});
	}
}
