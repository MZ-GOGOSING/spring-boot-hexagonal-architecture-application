package me.gogosing.board.application.port.out;

import javax.validation.constraints.Min;
import me.gogosing.board.domain.BoardDomainEntity;

public interface LoadBoardArticlePort {

	BoardDomainEntity findById(final @Min(1L) Long id);
}
