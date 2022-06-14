package me.gogosing.board.adapter.in.web.response.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.gogosing.board.adapter.in.web.response.GetBoardArticleWebResponse;
import me.gogosing.board.adapter.in.web.response.GetBoardAttachmentWebResponse;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.GetBoardAttachmentInResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class GetBoardArticleWebResponseConverter implements Converter<GetBoardArticleInResponse, GetBoardArticleWebResponse> {

	@Override
	public GetBoardArticleWebResponse convert(final @Nullable GetBoardArticleInResponse source) {
		return Optional.ofNullable(source)
			.map(inResponse -> GetBoardArticleWebResponse.builder()
				.id(inResponse.getId())
				.title(inResponse.getTitle())
				.contents(inResponse.getContents())
				.category(inResponse.getCategory())
				.createDate(inResponse.getCreateDate())
				.updateDate(inResponse.getUpdateDate())
				.attachments(convert(inResponse.getAttachments()))
				.build())
			.orElse(null);
	}

	private List<GetBoardAttachmentWebResponse> convert(final List<GetBoardAttachmentInResponse> sources) {
		final var attachmentWebConverter = new GetBoardArticleAttachmentWebResponseConverter();

		return CollectionUtils.emptyIfNull(sources)
			.stream()
			.map(attachmentWebConverter::convert)
			.collect(Collectors.toList());
	}
}
