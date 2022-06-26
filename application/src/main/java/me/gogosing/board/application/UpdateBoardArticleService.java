package me.gogosing.board.application;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.domain.ports.inside.UpdateBoardArticleUseCase;
import me.gogosing.board.application.port.in.request.command.UpdateBoardArticleInCommand;
import me.gogosing.board.application.port.in.request.command.UpdateBoardAttachmentInCommand;
import me.gogosing.board.application.port.in.response.UpdateBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.UpdateBoardAttachmentInResponse;
import me.gogosing.board.domain.ports.outside.CreateBoardAttachmentsPort;
import me.gogosing.board.domain.ports.outside.DeleteBoardAttachmentsPort;
import me.gogosing.board.domain.ports.outside.LoadBoardArticlePort;
import me.gogosing.board.domain.ports.outside.UpdateBoardArticlePort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.support.jta.JtaTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class UpdateBoardArticleService implements UpdateBoardArticleUseCase {

    private final LoadBoardArticlePort loadBoardArticlePort;

    private final UpdateBoardArticlePort updateBoardArticlePort;

    private final DeleteBoardAttachmentsPort deleteBoardAttachmentsPort;

    private final CreateBoardAttachmentsPort createBoardAttachmentsPort;

    @Override
    @JtaTransactional
    public UpdateBoardArticleInResponse updateBoardArticle(
        final Long id,
        final UpdateBoardArticleInCommand inCommand
    ) {
        final var boardArticleOutResponse = this.updateBoard(id, inCommand);
        final var boardAttachmentOutResponse =
            this.updateBoardAttachments(id, inCommand.getAttachments());

        return this.convertToInResponse(boardArticleOutResponse, boardAttachmentOutResponse);
    }

    private BoardDomainEntity updateBoard(
        final Long id,
        final UpdateBoardArticleInCommand inCommand
    ) {
        final var storedBoardDomainEntity = loadBoardArticlePort.loadBoardArticle(id);

        final var boardArticleOutCommand = this.convertToOutCommand(storedBoardDomainEntity, inCommand);

        return updateBoardArticlePort.updateBoardArticle(boardArticleOutCommand);
    }

    private List<BoardAttachmentDomainEntity> updateBoardAttachments(
        final Long boardId,
        final List<UpdateBoardAttachmentInCommand> inCommand
    ) {
        final var boardAttachmentOutCommand = this.convertToOutCommand(boardId, inCommand);

        deleteBoardAttachmentsPort.deleteBoardAttachments(boardId);

        return CollectionUtils.isEmpty(boardAttachmentOutCommand)
            ? Collections.emptyList()
            : createBoardAttachmentsPort.createBoardAttachments(boardAttachmentOutCommand);
    }

    private BoardDomainEntity convertToOutCommand(
        final BoardDomainEntity storedBoardDomainEntity,
        final UpdateBoardArticleInCommand inCommand
    ) {
        return BoardDomainEntity.withId(
            storedBoardDomainEntity.getId(),
            inCommand.getTitle(),
            inCommand.getCategory(),
            storedBoardDomainEntity.getCreateDate(),
            LocalDateTime.now(),
            inCommand.getContents()
        );
    }

    private List<BoardAttachmentDomainEntity> convertToOutCommand(
        final Long boardId,
        final List<UpdateBoardAttachmentInCommand> inCommand
    ) {
        return CollectionUtils.emptyIfNull(inCommand)
            .stream()
            .map(attachment -> BoardAttachmentDomainEntity.withoutId(
                boardId,
                attachment.getPath(),
                attachment.getName()
            ))
            .collect(Collectors.toList());
    }

    private UpdateBoardArticleInResponse convertToInResponse(
        final BoardDomainEntity boardArticleOutResponse,
        final List<BoardAttachmentDomainEntity> boardAttachmentOutResponse
    ) {
        final var attachmentResponseList = boardAttachmentOutResponse
            .stream()
            .map(domainEntity -> UpdateBoardAttachmentInResponse.builder()
                .id(domainEntity.getId())
                .boardId(domainEntity.getBoardId())
                .path(domainEntity.getPath())
                .name(domainEntity.getName())
                .build())
            .collect(Collectors.toList());

        return UpdateBoardArticleInResponse.builder()
            .id(boardArticleOutResponse.getId())
            .title(boardArticleOutResponse.getTitle())
            .category(boardArticleOutResponse.getCategory())
            .contents(boardArticleOutResponse.getContents())
            .createDate(boardArticleOutResponse.getCreateDate())
            .updateDate(boardArticleOutResponse.getUpdateDate())
            .attachments(attachmentResponseList)
            .build();
    }
}
