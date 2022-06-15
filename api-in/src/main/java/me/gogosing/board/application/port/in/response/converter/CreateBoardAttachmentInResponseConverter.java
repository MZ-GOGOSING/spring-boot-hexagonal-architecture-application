package me.gogosing.board.application.port.in.response.converter;

import java.util.Optional;
import me.gogosing.board.application.port.in.response.CreateBoardAttachmentInResponse;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class CreateBoardAttachmentInResponseConverter
    implements Converter<BoardAttachmentDomainEntity, CreateBoardAttachmentInResponse> {

    @Override
    public CreateBoardAttachmentInResponse convert(
        final @Nullable BoardAttachmentDomainEntity boardAttachmentDomainEntity
    ) {
        return Optional.ofNullable(boardAttachmentDomainEntity)
            .map(source -> CreateBoardAttachmentInResponse.builder()
                .id(source.getId())
                .boardId(source.getBoardId())
                .path(source.getPath())
                .name(source.getName())
                .build())
            .orElse(null);
    }
}
