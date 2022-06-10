package me.gogosing.board.adapter.in.web.request.command;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gogosing.board.application.port.in.request.command.BoardArticleCreationInCommand;
import me.gogosing.board.application.port.in.request.command.BoardAttachmentCreationInCommand;
import me.gogosing.support.code.board.BoardCategory;

@Schema(description = "게시물 등록 모델")
@Getter
@Setter
@NoArgsConstructor
public class BoardArticleCreationWebCommand {

	@NotBlank
	@Schema(description = "제목", example = "게시물 제목", required = true)
	private String title;

	@NotNull
	@Schema(description = "카테고리", example = "NORMAL", required = true)
	private BoardCategory category;

	@NotBlank
	@Schema(description = "내용", example = "게시물 내용", required = true)
	private String contents;

	@Valid
	@Schema(description = "첨부파일 목록")
	private List<BoardAttachmentCreationWebCommand> attachments = Collections.emptyList();


	public BoardArticleCreationInCommand toInCommand() {
		List<BoardAttachmentCreationInCommand> attachmentInCommand = attachments
			.stream()
			.map(attachment -> BoardAttachmentCreationInCommand.builder()
				.path(attachment.getPath())
				.name(attachment.getName())
				.build())
			.collect(Collectors.toList());

		return BoardArticleCreationInCommand.builder()
			.title(title)
			.category(category)
			.contents(contents)
			.attachments(attachmentInCommand)
			.build();
	}
}
