package me.gogosing.board.application.port.in.response;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.support.code.board.BoardCategory;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public final class GetBoardArticleItemInResponse {

    private final Long id;

    private final String title;

    private final BoardCategory category;

    private final LocalDateTime createDate;

    private final LocalDateTime updateDate;

    public static GetBoardArticleItemInResponse of(final BoardDomainEntity boardDomainEntity) {
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
