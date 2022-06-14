package me.gogosing.board.adapter.in.web.response.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.gogosing.board.adapter.in.web.response.CreateBoardArticleWebResponse;
import me.gogosing.board.adapter.in.web.response.CreateBoardAttachmentWebResponse;
import me.gogosing.board.application.port.in.response.CreateBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.CreateBoardAttachmentInResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class CreateBoardArticleWebResponseConverter implements Converter<CreateBoardArticleInResponse, CreateBoardArticleWebResponse> {

	@Override
	public CreateBoardArticleWebResponse convert(final @Nullable CreateBoardArticleInResponse source) {
		return Optional.ofNullable(source)
			.map(inResponse -> CreateBoardArticleWebResponse.builder()
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

	private List<CreateBoardAttachmentWebResponse> convert(final List<CreateBoardAttachmentInResponse> sources) {
		final var attachmentWebConverter = new CreateBoardArticleAttachmentWebResponseConverter();

		return CollectionUtils.emptyIfNull(sources)
			.stream()
			.map(attachmentWebConverter::convert)
			.collect(Collectors.toList());
	}
}
