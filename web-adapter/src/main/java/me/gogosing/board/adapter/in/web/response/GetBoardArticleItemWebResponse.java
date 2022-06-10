package me.gogosing.board.adapter.in.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.gogosing.support.code.board.BoardCategory;

@Schema(description = "게시판 항목 정보 응답 모델")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardArticleItemWebResponse {

	@Schema(description = "식별자", example = "1", required = true)
	private final Long id;

	@Schema(description = "제목", example = "게시물 제목", required = true)
	private final String title;

	@Schema(description = "카테고리", example = "NORMAL", required = true)
	private final BoardCategory category;

	@Schema(description = "등록일", example = "yyyy-MM-dd", required = true)
	private final LocalDateTime createDate;

	@Schema(description = "수정일", example = "yyyy-MM-dd", required = true)
	private final LocalDateTime updateDate;
}
