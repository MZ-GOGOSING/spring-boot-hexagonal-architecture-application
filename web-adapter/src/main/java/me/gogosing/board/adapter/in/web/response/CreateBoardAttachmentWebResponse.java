package me.gogosing.board.adapter.in.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.gogosing.board.application.port.in.response.CreateBoardAttachmentInResponse;
import org.apache.commons.collections4.CollectionUtils;

@Schema(description = "게시물 첨부파일 생성 응답 모델")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public final class CreateBoardAttachmentWebResponse {

	@Schema(description = "첨부파일 식별자", example = "1", required = true)
	private final Long id;

	@Schema(description = "소속 게시물 식별자", example = "1", required = true)
	private final Long boardId;

	@Schema(description = "경로", example = "https://host.com/foo/bar/", required = true)
	private final String path;

	@Schema(description = "파일명", example = "image.png", required = true)
	private final String name;

	public static List<CreateBoardAttachmentWebResponse> of(final List<CreateBoardAttachmentInResponse> inResponse) {
		return CollectionUtils.emptyIfNull(inResponse)
			.stream()
			.map(CreateBoardAttachmentWebResponse::of)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	private static CreateBoardAttachmentWebResponse of(final CreateBoardAttachmentInResponse inResponse) {
		return Optional.ofNullable(inResponse)
			.map(source -> CreateBoardAttachmentWebResponse.builder()
				.id(source.getId())
				.boardId(source.getBoardId())
				.path(source.getPath())
				.name(source.getName())
				.build())
			.orElse(null);
	}
}
