package me.gogosing.board.application.port.in.response.converter;

import java.util.Optional;
import me.gogosing.board.application.port.in.response.GetBoardArticleItemInResponse;
import me.gogosing.board.domain.BoardDomainEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class GetBoardArticleItemInResponseConverter
    implements Converter<BoardDomainEntity, GetBoardArticleItemInResponse> {

    @Override
    public GetBoardArticleItemInResponse convert(final @Nullable BoardDomainEntity boardDomainEntity) {
        return Optional.ofNullable(boardDomainEntity)
            .map(source -> GetBoardArticleItemInResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .category(source.getCategory())
                .createDate(source.getCreateDate())
                .updateDate(source.getUpdateDate())
                .build())
            .orElse(null);
    }
}
