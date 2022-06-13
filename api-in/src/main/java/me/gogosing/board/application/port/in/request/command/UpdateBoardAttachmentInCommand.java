package me.gogosing.board.application.port.in.request.command;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UpdateBoardAttachmentInCommand {

	@NotBlank
	@EqualsAndHashCode.Include
	private final String path;

	@NotBlank
	@EqualsAndHashCode.Include
	private final String name;
}
