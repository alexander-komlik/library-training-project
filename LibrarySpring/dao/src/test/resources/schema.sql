drop table t_book_author if exists;
drop table t_book if exists;
drop table t_author if exists;
drop table t_text if exists;
drop table t_accessData if exists;
drop table t_user if exists;

CREATE TABLE t_user
(
id bigint IDENTITY NOT NULL,
email VARCHAR(255) NOT NULL,
name VARCHAR(255) NOT NULL,
role VARCHAR(255) NOT NULL,
CONSTRAINT pk_user PRIMARY KEY (id),
UNIQUE (email),
UNIQUE (name)
);

CREATE TABLE t_accessData
(
id bigint IDENTITY NOT NULL,
pass VARCHAR(255) NOT NULL,
user_id bigint,
PRIMARY KEY(id),
CONSTRAINT pk_user_1 FOREIGN KEY (user_id) REFERENCES t_user (id)
);

CREATE TABLE t_text
(
id bigint IDENTITY NOT NULL,
text LONGVARCHAR,
CONSTRAINT pk_text PRIMARY KEY (id)
);

CREATE TABLE t_book
(
id bigint IDENTITY NOT NULL,
title VARCHAR(255),
lastUpdate TIMESTAMP,
text_id bigint,
user_id bigint,
CONSTRAINT pk_book PRIMARY KEY (id),
CONSTRAINT pk_text_1 FOREIGN KEY (text_id) REFERENCES t_text (id),
CONSTRAINT pk_user_2 FOREIGN KEY (user_id) REFERENCES t_user (id)
);

CREATE TABLE  t_author
(
id bigint IDENTITY NOT NULL,
name VARCHAR(255),
name4table VARCHAR(255),
text_id bigint,
user_id bigint,
CONSTRAINT pk_author PRIMARY KEY (id),
CONSTRAINT pk_text_2 FOREIGN KEY (text_id) REFERENCES t_text (id),
CONSTRAINT pk_user_3 FOREIGN KEY (user_id) REFERENCES t_user (id)
);

CREATE TABLE t_book_author
(
book_id bigint,
author_id bigint,
CONSTRAINT pk_book_1 FOREIGN KEY (book_id) REFERENCES t_book (id),
CONSTRAINT pk_author_1 FOREIGN KEY (author_id) REFERENCES t_author (id)
);
