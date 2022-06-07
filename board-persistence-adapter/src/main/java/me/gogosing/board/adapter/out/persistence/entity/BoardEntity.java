package me.gogosing.board.adapter.out.persistence.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BoardEntity extends BaseEntity {

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
	 * 사용여부.
	 */
	@Column(name = "board_use_yn", nullable = false)
	private boolean boardUseYn;

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