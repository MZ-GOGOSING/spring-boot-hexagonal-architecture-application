CREATE TABLE IF NOT EXISTS board (
    board_id BIGINT(5) AUTO_INCREMENT PRIMARY KEY COMMENT '식별자',
    board_title VARCHAR(250) NOT NULL COMMENT '제목',
    board_category VARCHAR(20) NOT NULL COMMENT '카테고리',
    deleted BIT(1) NOT NULL DEFAULT 0 COMMENT '삭제여부',
    create_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '생성일',
    update_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '최종수정일'
) COMMENT '게시물 테이블';

CREATE TABLE IF NOT EXISTS board_contents (
    board_id BIGINT(5) PRIMARY KEY COMMENT '게시물 식별자',
    board_contents TEXT NOT NULL COMMENT '게시물 컨텐츠 내용'
) COMMENT '게시물 컨텐츠 테이블';

CREATE TABLE IF NOT EXISTS board_attachment (
    board_attachment_id BIGINT(5) AUTO_INCREMENT PRIMARY KEY COMMENT '게시물 첨부파일 식별자',
    board_id BIGINT(5) NOT NULL COMMENT '게시물 식별자',
    board_attachment_path VARCHAR(150) NOT NULL COMMENT '첨부파일경로',
    board_attachment_name VARCHAR(100) NOT NULL COMMENT '첨부파일명'
) COMMENT '게시물 첨부파일 테이블';
