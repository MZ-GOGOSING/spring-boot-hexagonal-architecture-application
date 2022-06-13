package me.gogosing.board.application;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.UpdateBoardArticleUseCase;
import me.gogosing.board.application.port.in.request.command.UpdateBoardArticleInCommand;
import me.gogosing.board.application.port.in.request.command.UpdateBoardAttachmentInCommand;
import me.gogosing.board.application.port.in.response.UpdateBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.UpdateBoardAttachmentInResponse;
import me.gogosing.board.application.port.out.CreateBoardAttachmentsPort;
import me.gogosing.board.application.port.out.DeleteBoardAttachmentsPort;
import me.gogosing.board.application.port.out.LoadBoardArticlePort;
import me.gogosing.board.application.port.out.UpdateBoardArticlePort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public UpdateBoardArticleInResponse updateBoardArticle(
        final Long id,
        final UpdateBoardArticleInCommand inCommand
    ) {
        BoardDomainEntity storedBoardDomainEntity = loadBoardArticlePort.loadBoardArticle(id);

        BoardDomainEntity boardArticleOutCommand = convertToOutCommand(storedBoardDomainEntity, inCommand);
        BoardDomainEntity boardArticleOutResponse = updateBoardArticlePort
            .updateBoardArticle(boardArticleOutCommand);

        List<BoardAttachmentDomainEntity> boardAttachmentOutCommand =
            convertToOutCommand(id, inCommand.getAttachments());
        List<BoardAttachmentDomainEntity> boardAttachmentOutResponse =
            updateBoardAttachments(id, boardAttachmentOutCommand);

        return convertToInResponse(boardArticleOutResponse, boardAttachmentOutResponse);
    }

    private List<BoardAttachmentDomainEntity> updateBoardAttachments(
        final Long boardId,
        final List<BoardAttachmentDomainEntity> boardAttachmentOutCommand
    ) {
        if (CollectionUtils.isEmpty(boardAttachmentOutCommand)) {
            return Collections.emptyList();
        }

        deleteBoardAttachmentsPort.deleteBoardAttachments(boardId);

        return createBoardAttachmentsPort.createBoardAttachments(boardAttachmentOutCommand);
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
        List<UpdateBoardAttachmentInResponse> attachmentResponseList = boardAttachmentOutResponse
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
