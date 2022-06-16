package me.gogosing.board.adapter.in.web.request.command;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "게시물 첨부파일 수정 모델")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UpdateBoardAttachmentWebCommand {

	@NotBlank
	@EqualsAndHashCode.Include
	@Schema(description = "경로", example = "https://host.com/foo/bar/", required = true)
	private String path;

	@NotBlank
	@EqualsAndHashCode.Include
	@Schema(description = "파일명", example = "image.png", required = true)
	private String name;
}
