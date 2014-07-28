INSERT INTO t_user (id, email, name, role) VALUES(1, 'admin@gmail.com', 'admin', 'admin');
INSERT INTO t_user (id, email, name, role) VALUES(2, 'moder@gmail.com', 'moder', 'moderator');
INSERT INTO t_user (id, email, name, role) VALUES(3, 'user1@gmail.com', 'user1', 'user');
INSERT INTO t_user (id, email, name, role) VALUES(4, 'user2@gmail.com', 'user2', 'user');
INSERT INTO t_user (id, email, name, role) VALUES(5, 'guest@gmail.com', 'guest', 'guest');

INSERT INTO t_accessData(id, pass, user_id) VALUES (1, 'admin', 1);
INSERT INTO t_accessData(id, pass, user_id) VALUES (2, 'moder', 2);
INSERT INTO t_accessData(id, pass, user_id) VALUES (3, 'user1', 3);
INSERT INTO t_accessData(id, pass, user_id) VALUES (4, 'user2', 4);
INSERT INTO t_accessData(id, pass, user_id) VALUES (5, 'guest', 5);

INSERT INTO t_text(id, text) VALUES(1, 'About author Author1');
INSERT INTO t_text(id, text) VALUES(2, 'About author Author2');
INSERT INTO t_text(id, text) VALUES(3, 'Book1 text');
INSERT INTO t_text(id, text) VALUES(4, 'Book2 text');
INSERT INTO t_text(id, text) VALUES(5, 'Book3 text');
INSERT INTO t_text(id, text) VALUES(6, 'Book4 text');

INSERT INTO t_author(id, name, name4table, text_id, user_id) VALUES(1, 'Author number 1', 'Author1', 1, 3);
INSERT INTO t_author(id, name, name4table, text_id, user_id) VALUES(2, 'Author number 2', 'Author2', 2, 4);

INSERT INTO t_book(id, title, lastUpdate, text_id, user_id) VALUES(1, 'Book1', CURRENT_TIMESTAMP, 3, 3);
INSERT INTO t_book(id, title, lastUpdate, text_id, user_id) VALUES(2, 'Book2', CURRENT_TIMESTAMP, 4, 3);
INSERT INTO t_book(id, title, lastUpdate, text_id, user_id) VALUES(3, 'Book3', CURRENT_TIMESTAMP, 5, 3);
INSERT INTO t_book(id, title, lastUpdate, text_id, user_id) VALUES(4, 'Book4', CURRENT_TIMESTAMP, 6, 4);

INSERT INTO t_book_author(book_id, author_id) VALUES(1, 1);
INSERT INTO t_book_author(book_id, author_id) VALUES(2, 2);
INSERT INTO t_book_author(book_id, author_id) VALUES(3, 1);
INSERT INTO t_book_author(book_id, author_id) VALUES(3, 2);
INSERT INTO t_book_author(book_id, author_id) VALUES(4, 1);
INSERT INTO t_book_author(book_id, author_id) VALUES(3, 2);