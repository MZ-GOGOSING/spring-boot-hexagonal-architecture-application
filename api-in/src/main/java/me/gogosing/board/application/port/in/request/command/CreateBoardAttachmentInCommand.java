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
public class CreateBoardAttachmentInCommand {

	@NotBlank
	@EqualsAndHashCode.Include
	private String path;

	@NotBlank
	@EqualsAndHashCode.Include
	private String name;
}
