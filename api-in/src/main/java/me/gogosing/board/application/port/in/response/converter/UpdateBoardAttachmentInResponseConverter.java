package me.gogosing.board.application.port.in.response.converter;

import java.util.Optional;
import me.gogosing.board.application.port.in.response.UpdateBoardAttachmentInResponse;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class UpdateBoardAttachmentInResponseConverter
    implements Converter<BoardAttachmentDomainEntity, UpdateBoardAttachmentInResponse> {

    @Override
    public UpdateBoardAttachmentInResponse convert(
        final @Nullable BoardAttachmentDomainEntity boardAttachmentDomainEntity
    ) {
        return Optional.ofNullable(boardAttachmentDomainEntity)
            .map(source -> UpdateBoardAttachmentInResponse.builder()
                .id(source.getId())
                .boardId(source.getBoardId())
                .path(source.getPath())
                .name(source.getName())
                .build())
            .orElse(null);
    }
}
