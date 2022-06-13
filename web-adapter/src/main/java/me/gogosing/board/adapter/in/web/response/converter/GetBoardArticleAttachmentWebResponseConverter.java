package me.gogosing.board.adapter.in.web.response.converter;

import java.util.Optional;
import me.gogosing.board.adapter.in.web.response.GetBoardAttachmentWebResponse;
import me.gogosing.board.application.port.in.response.GetBoardAttachmentInResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class GetBoardArticleAttachmentWebResponseConverter implements Converter<GetBoardAttachmentInResponse, GetBoardAttachmentWebResponse> {

	@Override
	public GetBoardAttachmentWebResponse convert(final @Nullable GetBoardAttachmentInResponse source) {
		return Optional.ofNullable(source)
			.map(inResponse -> GetBoardAttachmentWebResponse.builder()
				.id(inResponse.getId())
				.boardId(inResponse.getBoardId())
				.path(inResponse.getPath())
				.name(inResponse.getName())
				.build())
			.orElse(null);
	}
}
