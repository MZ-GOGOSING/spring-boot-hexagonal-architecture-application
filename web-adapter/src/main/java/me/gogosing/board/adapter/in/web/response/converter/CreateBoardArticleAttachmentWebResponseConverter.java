package me.gogosing.board.adapter.in.web.response.converter;

import java.util.Optional;
import me.gogosing.board.adapter.in.web.response.CreateBoardAttachmentWebResponse;
import me.gogosing.board.application.port.in.response.CreateBoardAttachmentInResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class CreateBoardArticleAttachmentWebResponseConverter
	implements Converter<CreateBoardAttachmentInResponse, CreateBoardAttachmentWebResponse> {

	@Override
	public CreateBoardAttachmentWebResponse convert(final @Nullable CreateBoardAttachmentInResponse source) {
		return Optional.ofNullable(source)
			.map(inResponse -> CreateBoardAttachmentWebResponse.builder()
				.id(inResponse.getId())
				.boardId(inResponse.getBoardId())
				.path(inResponse.getPath())
				.name(inResponse.getName())
				.build())
			.orElse(null);
	}
}
