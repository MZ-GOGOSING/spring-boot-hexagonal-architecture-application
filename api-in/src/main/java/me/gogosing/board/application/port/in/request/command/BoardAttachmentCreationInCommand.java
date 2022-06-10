package me.gogosing.board.application.port.in.request.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardAttachmentCreationInCommand {

	private String path;

	private String name;
}
