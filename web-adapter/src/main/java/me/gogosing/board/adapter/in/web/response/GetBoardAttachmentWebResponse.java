package me.gogosing.board.adapter.in.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "게시물 첨부파일 응답 모델")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardAttachmentWebResponse {

	@Schema(description = "첨부파일 식별자", example = "1", required = true)
	private final Long id;

	@Schema(description = "소속 게시물 식별자", example = "1", required = true)
	private final Long boardId;

	@Schema(description = "경로", example = "https://host.com/foo/bar/", required = true)
	private final String path;

	@Schema(description = "파일명", example = "image.png", required = true)
	private final String name;
}
