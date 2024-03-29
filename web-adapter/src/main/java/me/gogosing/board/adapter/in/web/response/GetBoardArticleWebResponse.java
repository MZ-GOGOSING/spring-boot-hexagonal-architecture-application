package me.gogosing.board.adapter.in.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;
import me.gogosing.support.code.board.BoardCategory;

@Schema(description = "특정 게시물 정보 응답 모델")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public final class GetBoardArticleWebResponse {

	@Schema(description = "식별자", example = "1", required = true)
	private final Long id;

	@Schema(description = "제목", example = "게시물 제목", required = true)
	private final String title;

	@Schema(description = "카테고리", example = "NORMAL", required = true)
	private final BoardCategory category;

	@Schema(description = "내용", example = "게시물 내용", required = true)
	private final String contents;

	@Schema(description = "생성일", example = "yyyy-MM-dd", required = true)
	private final LocalDateTime createDate;

	@Schema(description = "수정일", example = "yyyy-MM-dd", required = true)
	private final LocalDateTime updateDate;

	@Schema(description = "첨부파일 목록")
	private final List<GetBoardAttachmentWebResponse> attachments;

	public static GetBoardArticleWebResponse of(final GetBoardArticleInResponse inResponse) {
		return Optional.ofNullable(inResponse)
			.map(source -> GetBoardArticleWebResponse.builder()
				.id(source.getId())
				.title(source.getTitle())
				.contents(source.getContents())
				.category(source.getCategory())
				.createDate(source.getCreateDate())
				.updateDate(source.getUpdateDate())
				.attachments(GetBoardAttachmentWebResponse.of(source.getAttachments()))
				.build())
			.orElse(null);
	}
}
