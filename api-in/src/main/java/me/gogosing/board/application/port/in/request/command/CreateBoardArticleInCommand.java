package me.gogosing.board.application.port.in.request.command;

import static me.gogosing.support.code.board.BoardCategory.NORMAL;
import static me.gogosing.support.code.board.BoardCategory.NOTICE;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.gogosing.support.code.board.BoardCategory;
import me.gogosing.support.validation.board.BoardCategorySubset;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardArticleInCommand {

	@NotBlank
	private final String title;

	@NotNull
	@BoardCategorySubset(anyOf = {NOTICE, NORMAL})
	private final BoardCategory category;

	@NotBlank
	private final String contents;

	@Valid
	private final List<CreateBoardAttachmentInCommand> attachments;
}
