package me.gogosing.support.code.board;

import lombok.Getter;
import me.gogosing.support.code.DescriptionCode;

@Getter
public enum BoardCategory implements DescriptionCode {
	NORMAL("일반"),
	NOTICE("공지사항");

	private final String description;

	BoardCategory(String description) {
		this.description = description;
	}
}
