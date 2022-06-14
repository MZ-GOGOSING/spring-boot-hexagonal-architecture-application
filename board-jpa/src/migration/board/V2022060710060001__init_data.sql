/* 게시물 샘플 데이터 */
INSERT INTO board (board_id, board_title, board_category, deleted, create_date, update_date)
VALUES (1, '첫번째 게시물 제목', 'NOTICE', false, current_timestamp(), current_timestamp());

INSERT INTO board (board_id, board_title, board_category, deleted, create_date, update_date)
VALUES (2, '두번째 게시물 제목', 'NORMAL', false, current_timestamp(), current_timestamp());

INSERT INTO board (board_id, board_title, board_category, deleted, create_date, update_date)
VALUES (3, '세번째 게시물 제목', 'NORMAL', false, current_timestamp(), current_timestamp());

INSERT INTO board (board_id, board_title, board_category, deleted, create_date, update_date)
VALUES (4, '네번째 게시물 제목', 'NORMAL', false, current_timestamp(), current_timestamp());

INSERT INTO board (board_id, board_title, board_category, deleted, create_date, update_date)
VALUES (5, '다섯번째 게시물 제목', 'NORMAL', false, current_timestamp(), current_timestamp());

/* 게시물 컨텐츠 샘플 데이터 */
INSERT INTO board_contents (board_id, board_contents) VALUES (1 , '첫번째 게시물 내용');
INSERT INTO board_contents (board_id, board_contents) VALUES (2 , '두번째 게시물 내용');
INSERT INTO board_contents (board_id, board_contents) VALUES (3 , '세번째 게시물 내용');
INSERT INTO board_contents (board_id, board_contents) VALUES (4 , '네번째 게시물 내용');
INSERT INTO board_contents (board_id, board_contents) VALUES (5 , '다섯번째 게시물 내용');