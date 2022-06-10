package me.gogosing.board.adapter.in.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "게시물 첨부파일 생성 응답 모델")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardAttachmentWebResponse {

	@Schema(description = "경로", example = "https://host.com/foo/bar/", required = true)
	private final String path;

	@Schema(description = "파일명", example = "image.png", required = true)
	private final String name;
}
