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
public final class UpdateBoardAttachmentInResponse {

	private final Long id;

	private final Long boardId;

	private final String path;

	private final String name;

	public static List<UpdateBoardAttachmentInResponse> of(
		final List<BoardAttachmentDomainEntity> boardAttachmentDomainEntities
	) {
		return CollectionUtils.emptyIfNull(boardAttachmentDomainEntities)
			.stream()
			.map(UpdateBoardAttachmentInResponse::of)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	private static UpdateBoardAttachmentInResponse of(
		final BoardAttachmentDomainEntity boardAttachmentDomainEntity
	) {
		return Optional.ofNullable(boardAttachmentDomainEntity)
			.map(source -> UpdateBoardAttachmentInResponse.builder()
				.id(source.getId())
				.boardId(source.getBoardId())
				.path(source.getPath())
				.name(source.getName())
				.build())
			.orElse(null);
	}
}
