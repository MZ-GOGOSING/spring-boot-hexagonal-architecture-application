package me.gogosing.board.adapter.in.web.response.converter;

import java.util.Optional;
import me.gogosing.board.adapter.in.web.response.UpdateBoardAttachmentWebResponse;
import me.gogosing.board.application.port.in.response.UpdateBoardAttachmentInResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class UpdateBoardArticleAttachmentWebResponseConverter
	implements Converter<UpdateBoardAttachmentInResponse, UpdateBoardAttachmentWebResponse> {

	@Override
	public UpdateBoardAttachmentWebResponse convert(final @Nullable UpdateBoardAttachmentInResponse source) {
		return Optional.ofNullable(source)
			.map(inResponse -> UpdateBoardAttachmentWebResponse.builder()
				.id(inResponse.getId())
				.boardId(inResponse.getBoardId())
				.path(inResponse.getPath())
				.name(inResponse.getName())
				.build())
			.orElse(null);
	}
}
