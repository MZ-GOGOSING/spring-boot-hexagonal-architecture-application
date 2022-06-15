package me.gogosing.board.application.port.in.response.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.gogosing.board.application.port.in.response.UpdateBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.UpdateBoardAttachmentInResponse;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.support.converter.BiConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.Nullable;

public class UpdateBoardArticleInResponseConverter
    implements BiConverter<BoardDomainEntity, List<BoardAttachmentDomainEntity>, UpdateBoardArticleInResponse> {

    @Override
    public UpdateBoardArticleInResponse convert(
        final @Nullable BoardDomainEntity boardDomainEntity,
        final @Nullable List<BoardAttachmentDomainEntity> boardAttachmentDomainEntities
    ) {
        return Optional.ofNullable(boardDomainEntity)
            .map(source -> UpdateBoardArticleInResponse.builder()
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

    private List<UpdateBoardAttachmentInResponse> convert(
        final List<BoardAttachmentDomainEntity> boardAttachmentDomainEntities
    ) {
        final var attachmentInResponseConverter = new UpdateBoardAttachmentInResponseConverter();

        return CollectionUtils.emptyIfNull(boardAttachmentDomainEntities)
            .stream()
            .map(attachmentInResponseConverter::convert)
            .collect(Collectors.toList());
    }
}
