package me.gogosing.board.adapter.in.web.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardAttachmentWebResponse {

	private final String path;

	private final String name;
}
