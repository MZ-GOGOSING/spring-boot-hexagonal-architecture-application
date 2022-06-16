package me.gogosing.board.application.port.in.response;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import org.apache.commons.collections4.CollectionUtils;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public final class CreateBoardAttachmentInResponse {

	private final Long id;

	private final Long boardId;

	private final String path;

	private final String name;

	public static List<CreateBoardAttachmentInResponse> of(
		final List<BoardAttachmentDomainEntity> boardAttachmentDomainEntities
	) {
		return CollectionUtils.emptyIfNull(boardAttachmentDomainEntities)
			.stream()
			.map(CreateBoardAttachmentInResponse::of)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	private static CreateBoardAttachmentInResponse of(
		final BoardAttachmentDomainEntity boardAttachmentDomainEntity
	) {
		return Optional.ofNullable(boardAttachmentDomainEntity)
			.map(source -> CreateBoardAttachmentInResponse.builder()
				.id(source.getId())
				.boardId(source.getBoardId())
				.path(source.getPath())
				.name(source.getName())
				.build())
			.orElse(null);
	}
}
