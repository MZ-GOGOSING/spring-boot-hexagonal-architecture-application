package me.gogosing.board.application.port.in.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.gogosing.support.code.board.BoardCategory;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardArticleInResponse {

	private final Long id;

	private final String title;

	private final BoardCategory category;

	private final String contents;

	private final LocalDateTime createDate;

	private final LocalDateTime updateDate;

	private final List<GetBoardAttachmentInResponse> attachments;
}
