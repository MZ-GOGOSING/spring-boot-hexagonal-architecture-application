package me.gogosing.board.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.board.application.port.in.CreateBoardArticleUseCase;
import me.gogosing.board.application.port.in.request.command.CreateBoardArticleInCommand;
import me.gogosing.board.application.port.in.response.CreateBoardArticleInResponse;
import me.gogosing.board.application.port.in.response.CreateBoardAttachmentInResponse;
import me.gogosing.board.application.port.out.CreateBoardArticlePort;
import me.gogosing.board.domain.BoardAttachmentDomainEntity;
import me.gogosing.board.domain.BoardDomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CreateBoardArticleService implements CreateBoardArticleUseCase {

    private final CreateBoardArticlePort createBoardArticlePort;

    @Override
    @Transactional
    public CreateBoardArticleInResponse createBoardArticle(final CreateBoardArticleInCommand inCommand) {
        BoardDomainEntity outCommand = convertToOutCommand(inCommand);
        BoardDomainEntity outResponse = createBoardArticlePort
            .createBoardArticle(outCommand);

        return convertToInResponse(outResponse);
    }

    private BoardDomainEntity convertToOutCommand(final CreateBoardArticleInCommand inCommand) {
        List<BoardAttachmentDomainEntity> attachments = inCommand.getAttachments()
            .stream()
            .map(attachment -> BoardAttachmentDomainEntity.withoutId(
                attachment.getPath(),
                attachment.getName()
            ))
            .collect(Collectors.toList());

        return BoardDomainEntity.withoutId(
            inCommand.getTitle(),
            inCommand.getCategory(),
            inCommand.getContents(),
            attachments
        );
    }

    private CreateBoardArticleInResponse convertToInResponse(final BoardDomainEntity outResponse) {
        List<CreateBoardAttachmentInResponse> attachmentResponseList = outResponse.getAttachments()
            .stream()
            .map(domainEntity -> CreateBoardAttachmentInResponse.builder()
                .id(domainEntity.getId())
                .path(domainEntity.getPath())
                .name(domainEntity.getName())
                .build())
            .collect(Collectors.toList());

        return CreateBoardArticleInResponse.builder()
            .id(outResponse.getId())
            .title(outResponse.getTitle())
            .category(outResponse.getCategory())
            .contents(outResponse.getContents())
            .createDate(outResponse.getCreateDate())
            .updateDate(outResponse.getUpdateDate())
            .attachments(attachmentResponseList)
            .build();
    }
}
