package me.gogosing.board.adapter.out.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 게시물 컨텐츠 Entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board_contents")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BoardContentsJpaEntity extends BaseJpaEntity {

	/**
	 * 게시물 번호.
	 */
	@Id
	@Column(name = "board_id", nullable = false)
	private Long boardId;

	/**
	 * 게시물 컨텐츠 내용.
	 */
	@Column(name = "board_contents", nullable = false, columnDefinition = "TEXT")
	private String boardContents;
}
