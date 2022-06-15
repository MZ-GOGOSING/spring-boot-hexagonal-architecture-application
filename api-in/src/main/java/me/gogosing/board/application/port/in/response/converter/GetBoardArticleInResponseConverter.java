package me.gogosing.board.application.port.in.response.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.gogosing.board.application.port.in.response.GetBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.GetBoardAttachmentInResponse;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.support.converter.BiConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.Nullable;

public class GetBoardArticleInResponseConverter
    implements BiConverter<BoardDomainEntity, List<BoardAttachmentDomainEntity>, GetBoardArticleInResponse> {

    @Override
    public GetBoardArticleInResponse convert(
        final @Nullable BoardDomainEntity boardDomainEntity,
        final @Nullable List<BoardAttachmentDomainEntity> boardAttachmentDomainEntities
    ) {
        return Optional.ofNullable(boardDomainEntity)
            .map(source -> GetBoardArticleInResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .category(source.getCategory())
                .contents(source.getContents())
                .createDate(source.getCreateDate())
                .updateDate(source.getUpdateDate())
                .attachments(convert(boardAttachmentDomainEntities))
                .build())
            .orElse(null);
    }

    private List<GetBoardAttachmentInResponse> convert(
        final List<BoardAttachmentDomainEntity> boardAttachmentDomainEntities
    ) {
        final var attachmentInResponseConverter = new GetBoardAttachmentInResponseConverter();

        return CollectionUtils.emptyIfNull(boardAttachmentDomainEntities)
            .stream()
            .map(attachmentInResponseConverter::convert)
            .collect(Collectors.toList());
    }
}
