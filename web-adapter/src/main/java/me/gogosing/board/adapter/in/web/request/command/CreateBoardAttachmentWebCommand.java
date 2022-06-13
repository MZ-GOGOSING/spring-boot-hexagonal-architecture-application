package me.gogosing.board.adapter.in.web.request.command;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "게시물 첨부파일 등록 모델")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CreateBoardAttachmentWebCommand {

	@NotBlank
	@EqualsAndHashCode.Include
	@Schema(description = "경로", example = "https://host.com/foo/bar/", required = true)
	private String path;

	@NotBlank
	@EqualsAndHashCode.Include
	@Schema(description = "파일명", example = "image.png", required = true)
	private String name;
}
