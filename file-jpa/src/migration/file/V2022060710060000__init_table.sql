CREATE TABLE IF NOT EXISTS board_attachment (
    board_attachment_id BIGINT(5) AUTO_INCREMENT PRIMARY KEY COMMENT '게시물 첨부파일 식별자',
    board_id BIGINT(5) NOT NULL COMMENT '게시물 식별자',
    board_attachment_path VARCHAR(150) NOT NULL COMMENT '첨부파일경로',
    board_attachment_name VARCHAR(100) NOT NULL COMMENT '첨부파일명'
) COMMENT '게시물 첨부파일 테이블';
