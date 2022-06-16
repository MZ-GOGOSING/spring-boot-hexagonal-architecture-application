package me.gogosing.board.application.port.in.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.support.code.board.BoardCategory;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class GetBoardArticleInResponse {

	private final Long id;

	private final String title;

	private final BoardCategory category;

	private final String contents;

	private final LocalDateTime createDate;

	private final LocalDateTime updateDate;

	private final List<GetBoardAttachmentInResponse> attachments;

	public static GetBoardArticleInResponse of(
		final BoardDomainEntity boardDomainEntity,
		final List<BoardAttachmentDomainEntity> boardAttachmentDomainEntities
	) {
		return Optional.ofNullable(boardDomainEntity)
			.map(source -> GetBoardArticleInResponse.builder()
				.id(source.getId())
				.title(source.getTitle())
				.category(source.getCategory())
				.contents(source.getContents())
				.createDate(source.getCreateDate())
				.updateDate(source.getUpdateDate())
				.attachments(GetBoardAttachmentInResponse.of(boardAttachmentDomainEntities))
				.build())
			.orElse(null);
	}
}
