package me.gogosing.board.application.port.in.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardAttachmentInResponse {

	private final Long id;

	private final String path;

	private final String name;
}
