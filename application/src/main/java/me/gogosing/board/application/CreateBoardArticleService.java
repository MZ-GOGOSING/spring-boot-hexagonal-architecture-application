package me.gogosing.board.application;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.CreateBoardArticleUseCase;
import me.gogosing.board.application.port.in.request.command.CreateBoardArticleInCommand;
import me.gogosing.board.application.port.in.request.command.CreateBoardAttachmentInCommand;
import me.gogosing.board.application.port.in.response.CreateBoardArticleInResponse;
import me.gogosing.board.application.port.out.CreateBoardArticlePort;
import me.gogosing.board.application.port.out.CreateBoardAttachmentsPort;
import me.gogosing.board.application.port.in.response.converter.CreateBoardArticleInResponseConverter;
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
    public CreateBoardArticleInResponse save(final CreateBoardArticleInCommand inCommand) {
        final var createdBoardDomainEntity = this.saveBoard(inCommand);
        final var createdBoardAttachmentDomainEntities =
            this.saveAllBoardAttachments(createdBoardDomainEntity.getId(), inCommand.getAttachments());

        return new CreateBoardArticleInResponseConverter()
            .convert(createdBoardDomainEntity, createdBoardAttachmentDomainEntities);
    }

    private BoardDomainEntity saveBoard(final CreateBoardArticleInCommand inCommand) {
        final var outCommand = this.convertToOutCommand(inCommand);

        return createBoardArticlePort.save(outCommand);
    }

    private List<BoardAttachmentDomainEntity> saveAllBoardAttachments(
        final Long boardId,
        final List<CreateBoardAttachmentInCommand> inCommand
    ) {
        if (CollectionUtils.isEmpty(inCommand)) {
            return Collections.emptyList();
        }

        final var outCommand = this.convertToOutCommand(boardId, inCommand);

        return createBoardAttachmentsPort.saveAll(outCommand);
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
}
