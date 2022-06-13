package me.gogosing.jpa.board.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gogosing.support.code.board.BoardCategory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * 게시물 Entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board")
public class BoardJpaEntity extends BaseJpaEntity {

	/**
	 * 일련번호.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id", nullable = false)
	private Long boardId;

	/**
	 * 제목.
	 */
	@Column(name = "board_title", nullable = false)
	private String boardTitle;

	/**
	 * 카테고리.
	 */
	@Enumerated(value = EnumType.STRING)
	@Column(name = "board_category", nullable = false)
	private BoardCategory boardCategory;

	/**
	 * 사용여부.
	 */
	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	/**
	 * 등록일시.
	 */
	@CreatedDate
	@Column(name = "create_date", nullable = false, updatable = false)
	private LocalDateTime createDate;

	/**
	 * 수정일시.
	 */
	@LastModifiedDate
	@Column(name = "update_date")
	private LocalDateTime updateDate;
}