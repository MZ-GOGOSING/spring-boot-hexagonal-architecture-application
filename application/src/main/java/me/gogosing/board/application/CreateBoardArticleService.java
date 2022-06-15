package me.gogosing.board.application;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.CreateBoardArticleUseCase;
import me.gogosing.board.application.port.in.request.command.CreateBoardArticleInCommand;
import me.gogosing.board.application.port.in.request.command.CreateBoardAttachmentInCommand;
import me.gogosing.board.application.port.in.response.CreateBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.CreateBoardAttachmentInResponse;
import me.gogosing.board.application.port.out.CreateBoardArticlePort;
import me.gogosing.board.application.port.out.CreateBoardAttachmentsPort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import me.gogosing.support.jta.JtaTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CreateBoardArticleService implements CreateBoardArticleUseCase {

    private final CreateBoardArticlePort createBoardArticlePort;

    private final CreateBoardAttachmentsPort createBoardAttachmentsPort;

    @Override
    @JtaTransactional
    public CreateBoardArticleInResponse createBoardArticle(final CreateBoardArticleInCommand inCommand) {
        final var createdBoardDomainEntity = this.saveBoard(inCommand);
        final var createdBoardAttachmentDomainEntities =
            this.saveBoardAttachments(createdBoardDomainEntity.getId(), inCommand.getAttachments());

        return this.convertToInResponse(createdBoardDomainEntity, createdBoardAttachmentDomainEntities);
    }

    private BoardDomainEntity saveBoard(final CreateBoardArticleInCommand inCommand) {
        final var boardDomainCreationOutCommand = this.convertToOutCommand(inCommand);

        return createBoardArticlePort.createBoardArticle(boardDomainCreationOutCommand);
    }

    private List<BoardAttachmentDomainEntity> saveBoardAttachments(
        final Long boardId,
        final List<CreateBoardAttachmentInCommand> inCommand
    ) {
        if (CollectionUtils.isEmpty(inCommand)) {
            return Collections.emptyList();
        }

        final var boardAttachmentsCreationOutCommand =
            this.convertToOutCommand(boardId, inCommand);

        return createBoardAttachmentsPort.createBoardAttachments(boardAttachmentsCreationOutCommand);
    }

    private BoardDomainEntity convertToOutCommand(
        final CreateBoardArticleInCommand inCommand
    ) {
        return BoardDomainEntity.withoutId(
            inCommand.getTitle(),
            inCommand.getCategory(),
            inCommand.getContents()
        );
    }

    private List<BoardAttachmentDomainEntity> convertToOutCommand(
        final Long boardId,
        final List<CreateBoardAttachmentInCommand> inCommand
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

    private CreateBoardArticleInResponse convertToInResponse(
        final BoardDomainEntity boardArticleOutResponse,
        final List<BoardAttachmentDomainEntity> boardAttachmentOutResponse
    ) {
        final var attachmentResponseList = boardAttachmentOutResponse
            .stream()
            .map(domainEntity -> CreateBoardAttachmentInResponse.builder()
                .id(domainEntity.getId())
                .boardId(domainEntity.getBoardId())
                .path(domainEntity.getPath())
                .name(domainEntity.getName())
                .build())
            .collect(Collectors.toList());

        return CreateBoardArticleInResponse.builder()
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
