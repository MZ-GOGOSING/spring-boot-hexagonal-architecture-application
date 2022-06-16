package me.gogosing.board.adapter.in.web.request.command;

import static me.gogosing.support.code.board.BoardCategory.NORMAL;
import static me.gogosing.support.code.board.BoardCategory.NOTICE;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gogosing.board.application.port.in.request.command.CreateBoardArticleInCommand;
import me.gogosing.board.application.port.in.request.command.CreateBoardAttachmentInCommand;
import me.gogosing.support.code.board.BoardCategory;
import me.gogosing.support.validation.board.BoardCategorySubset;

@Schema(description = "게시물 등록 모델")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardArticleWebCommand {

	@NotBlank
	@Schema(description = "제목", example = "게시물 제목", required = true)
	private String title;

	@NotNull
	@BoardCategorySubset(anyOf = {NOTICE, NORMAL})
	@Schema(description = "카테고리", example = "NORMAL", required = true)
	private BoardCategory category;

	@NotBlank
	@Schema(description = "내용", example = "게시물 내용", required = true)
	private String contents;

	@Valid
	@Schema(description = "첨부파일 목록")
	private List<CreateBoardAttachmentWebCommand> attachments = Collections.emptyList();


	public CreateBoardArticleInCommand toInCommand() {
		final var attachmentInCommand = this.attachments
			.stream()
			.map(attachment -> CreateBoardAttachmentInCommand.builder()
				.path(attachment.getPath())
				.name(attachment.getName())
				.build())
			.collect(Collectors.toList());

		return CreateBoardArticleInCommand.builder()
			.title(this.title)
			.category(this.category)
			.contents(this.contents)
			.attachments(attachmentInCommand)
			.build();
	}
}
