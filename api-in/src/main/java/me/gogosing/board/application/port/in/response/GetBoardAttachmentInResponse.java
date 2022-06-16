package me.gogosing.board.application.port.in.response;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import org.apache.commons.collections4.CollectionUtils;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class GetBoardAttachmentInResponse {

	private final Long id;

	private final Long boardId;

	private final String path;

	private final String name;

	public static List<GetBoardAttachmentInResponse> of(
		final List<BoardAttachmentDomainEntity> boardAttachmentDomainEntities
	) {
		return CollectionUtils.emptyIfNull(boardAttachmentDomainEntities)
			.stream()
			.map(GetBoardAttachmentInResponse::of)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	private static GetBoardAttachmentInResponse of(
		final BoardAttachmentDomainEntity boardAttachmentDomainEntity
	) {
		return Optional.ofNullable(boardAttachmentDomainEntity)
			.map(source -> GetBoardAttachmentInResponse.builder()
				.id(source.getId())
				.boardId(source.getBoardId())
				.path(source.getPath())
				.name(source.getName())
				.build())
			.orElse(null);
	}
}
