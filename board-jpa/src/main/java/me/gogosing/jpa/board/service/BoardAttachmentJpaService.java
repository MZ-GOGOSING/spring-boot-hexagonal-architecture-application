package me.gogosing.jpa.board.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.gogosing.jpa.board.config.BoardJpaTransactional;
import me.gogosing.jpa.board.entity.BoardAttachmentJpaEntity;
import me.gogosing.jpa.board.repository.BoardAttachmentJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
@RequiredArgsConstructor
public class BoardAttachmentJpaService {

	private final BoardAttachmentJpaRepository boardAttachmentJpaRepository;

	@BoardJpaTransactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Long, List<BoardAttachmentJpaEntity>> findAllByBoardId(final Long boardId) {
		return boardAttachmentJpaRepository.findAllByBoardId(boardId)
			.stream()
			.collect(Collectors.groupingBy(BoardAttachmentJpaEntity::getBoardId, Collectors.toList()));
	}
}
