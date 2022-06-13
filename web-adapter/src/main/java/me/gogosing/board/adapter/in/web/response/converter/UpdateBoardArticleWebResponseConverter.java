package me.gogosing.board.adapter.in.web.response.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.gogosing.board.adapter.in.web.response.UpdateBoardArticleWebResponse;
import me.gogosing.board.adapter.in.web.response.UpdateBoardAttachmentWebResponse;
import me.gogosing.board.application.port.in.response.UpdateBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.UpdateBoardAttachmentInResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class UpdateBoardArticleWebResponseConverter implements Converter<UpdateBoardArticleInResponse, UpdateBoardArticleWebResponse> {

	@Override
	public UpdateBoardArticleWebResponse convert(final @Nullable UpdateBoardArticleInResponse source) {
		return Optional.ofNullable(source)
			.map(inResponse -> UpdateBoardArticleWebResponse.builder()
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

	private List<UpdateBoardAttachmentWebResponse> convert(final List<UpdateBoardAttachmentInResponse> sources) {
		UpdateBoardArticleAttachmentWebResponseConverter attachmentWebConverter = new UpdateBoardArticleAttachmentWebResponseConverter();

		return CollectionUtils.emptyIfNull(sources)
			.stream()
			.map(attachmentWebConverter::convert)
			.collect(Collectors.toList());
	}
}
