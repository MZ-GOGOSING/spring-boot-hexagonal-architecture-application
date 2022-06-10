package me.gogosing.board.application.port.in.request.command;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.gogosing.support.code.board.BoardCategory;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardArticleCreationInCommand {

	private String title;

	private BoardCategory category;

	private String contents;

	private List<BoardAttachmentCreationInCommand> attachments;
}
